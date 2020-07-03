package com.skafenko.core.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ExceptionWhileDataFetching;

public class SanitizedException extends ExceptionWhileDataFetching {
    public SanitizedException(ExceptionWhileDataFetching inner) {
        super(inner.getException());
    }

    @Override
    @JsonIgnore
    public Throwable getException() {
        return super.getException();
    }
}
