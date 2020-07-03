package com.skafenko.core.context;

import com.skafenko.core.model.User;
import graphql.servlet.GraphQLContext;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Getter
public class AuthContext extends GraphQLContext {
    private final User user;

    public AuthContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response, User user) {
        super(request, response);
        this.user = user;
    }
}
