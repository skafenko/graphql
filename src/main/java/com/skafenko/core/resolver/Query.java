package com.skafenko.core.resolver;

import com.skafenko.core.model.Link;
import com.skafenko.core.model.LinkFilter;
import com.skafenko.core.repository.LinkRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class Query {
    private final LinkRepository repository;

    public Query(LinkRepository repository) {
        this.repository = repository;
    }

    @GraphQLQuery
    public List<Link> allLinks(LinkFilter filter,
                               @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
                               @GraphQLArgument(name = "first", defaultValue = "0") Number first) {
        return repository.getAllLinks(filter, skip.intValue(), first.intValue());
    }


}
