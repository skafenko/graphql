package com.skafenko.core.resolver;

import com.skafenko.core.model.Link;
import com.skafenko.core.model.User;
import com.skafenko.core.repository.UserRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class LinkResolver {
    private final UserRepository userRepository;

    public LinkResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GraphQLQuery
    public User postedBy(@GraphQLContext Link link) {
        if (link.getUserId() == null) {
            return null;
        }
        return userRepository.findById(link.getUserId());
    }
}
