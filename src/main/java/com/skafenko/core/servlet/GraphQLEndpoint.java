package com.skafenko.core.servlet;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.skafenko.core.context.AuthContext;
import com.skafenko.core.exception.SanitizedException;
import com.skafenko.core.model.User;
import com.skafenko.core.repository.LinkRepository;
import com.skafenko.core.repository.UserRepository;
import com.skafenko.core.repository.VoteRepository;
import com.skafenko.core.repository.impl.MongoLinkRepository;
import com.skafenko.core.repository.impl.MongoUserRepository;
import com.skafenko.core.repository.impl.MongoVoteRepository;
import com.skafenko.core.resolver.*;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.jetbrains.annotations.NotNull;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {
    private static final LinkRepository linkRepository;
    private static final UserRepository userRepository;
    private static final VoteRepository voteRepository;

    static {
        MongoDatabase mongo = new MongoClient().getDatabase("hackernews");
        linkRepository = new MongoLinkRepository(mongo.getCollection("links"));
        userRepository = new MongoUserRepository(mongo.getCollection("users"));
        voteRepository = new MongoVoteRepository(mongo.getCollection("votes"));
//        linkRepository = new InMemoryLinkRepository();
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    @NotNull
    private static GraphQLSchema buildSchema() {
        return new GraphQLSchemaGenerator()
                .withOperationsFromSingletons(
                        new Query(linkRepository),
                        new Mutation(linkRepository, userRepository, voteRepository),
                        new LinkResolver(userRepository))
                .generate();
    }

    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        User user = request
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", ""))
                .map(userRepository::findById)
                .orElse(null);
        return new AuthContext(request, response, user);
    }

    @Override
    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
        return errors.stream()
                .filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e))
                .map(e -> e instanceof ExceptionWhileDataFetching ? new SanitizedException((ExceptionWhileDataFetching) e) : e)
                .collect(Collectors.toList());
    }
}
