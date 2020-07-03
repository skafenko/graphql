package com.skafenko.core.repository;

import com.skafenko.core.model.Link;
import com.skafenko.core.model.LinkFilter;

import java.util.List;

public interface LinkRepository {
    Link findById(String id);

    List<Link> getAllLinks(LinkFilter filter, int skip, int first);

    List<Link> getAllLinks();

    void saveLink(Link link);
}
