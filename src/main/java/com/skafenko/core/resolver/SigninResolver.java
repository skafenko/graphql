package com.skafenko.core.resolver;

import com.skafenko.core.model.SigninPayload;
import com.skafenko.core.model.User;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class SigninResolver {

    @GraphQLQuery
    public User user(@GraphQLContext SigninPayload payload) {
        return payload.getUser();
    }
}
