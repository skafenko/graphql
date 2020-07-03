package com.skafenko.core.repository.impl;

import com.google.common.base.Strings;
import com.skafenko.core.model.Link;
import com.skafenko.core.model.LinkFilter;
import com.skafenko.core.repository.LinkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryLinkRepository implements LinkRepository {
    private final List<Link> links;

    public InMemoryLinkRepository() {
        links = new ArrayList<>();
        //add some links to start off with
        links.add(new Link("http://howtographql.com", "Your favorite GraphQL page", null));
        links.add(new Link("http://graphql.org/learn/", "The official docks", null));
    }

    @Override
    public Link findById(String id) {
        return links.stream()
                .filter(link -> id.equalsIgnoreCase(link.getId()))
                .findFirst().orElse(null);
    }

    @Override
    public List<Link> getAllLinks(LinkFilter filter, int skip, int first) {
        return links.stream()
                .filter(link -> {
                    String descriptionContains = filter.getDescriptionContains();
                    if (!Strings.isNullOrEmpty(descriptionContains)) {
                        return link.getDescription().contains(descriptionContains);
                    }
                    return true;
                })
                .filter(link -> {
                    String urlContains = filter.getUrlContains();
                    if (!Strings.isNullOrEmpty(urlContains)) {
                        return link.getUrl().contains(urlContains);
                    }
                    return true;
                })
                .skip(skip)
                .limit(first)
                .collect(Collectors.toList());
    }

    @Override
    public List<Link> getAllLinks() {
        return links;
    }

    @Override
    public void saveLink(Link link) {
        links.add(link);
    }
}
