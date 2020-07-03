package com.skafenko.core.resolver;

import com.skafenko.core.model.Link;
import com.skafenko.core.model.User;
import com.skafenko.core.model.Vote;
import com.skafenko.core.repository.LinkRepository;
import com.skafenko.core.repository.UserRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class VoteResolver {
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public VoteResolver(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    @GraphQLQuery
    public User user(@GraphQLContext Vote vote) {
        return userRepository.findById(vote.getUserId());
    }

    @GraphQLQuery
    public Link link(@GraphQLContext Vote vote) {
        return linkRepository.findById(vote.getLinkId());
    }
}
