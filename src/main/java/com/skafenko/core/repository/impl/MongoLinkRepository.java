package com.skafenko.core.repository.impl;

import com.google.common.base.Strings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.skafenko.core.model.Link;
import com.skafenko.core.model.LinkFilter;
import com.skafenko.core.repository.LinkRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.*;

public class MongoLinkRepository implements LinkRepository {
    private final MongoCollection<Document> links;

    public MongoLinkRepository(MongoCollection<Document> links) {
        this.links = links;
    }

    @Override
    public Link findById(String id) {
        Document doc = links.find(eq("_id", new ObjectId(id))).first();
        return link(doc);
    }

    @Override
    public List<Link> getAllLinks(LinkFilter filter, int skip, int first) {
        Optional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);
        List<Link> allLinks = new ArrayList<>();
        FindIterable<Document> findIterable = mongoFilter.map(links::find).orElseGet(links::find);
        for (Document doc : findIterable.skip(skip).limit(first)) {
            allLinks.add(link(doc));
        }
        return allLinks;
    }

    @Override
    public List<Link> getAllLinks() {
        List<Link> allLinks = new ArrayList<>();
        for (Document doc : links.find()) {
            Link link = new Link(
                    doc.get("_id").toString(),
                    doc.getString("url"),
                    doc.getString("description"),
                    doc.getString("postedBy")
            );
            allLinks.add(link);
        }
        return allLinks;
    }

    @Override
    public void saveLink(Link link) {
        Document doc = new Document();
        doc.append("url", link.getUrl());
        doc.append("description", link.getDescription());
        doc.append("postedBy", link.getUserId());
        links.insertOne(doc);
    }

    private Link link(Document doc) {
        return new Link(
                doc.get("_id").toString(),
                doc.getString("url"),
                doc.getString("description"),
                doc.getString("postedBy"));
    }

    private Bson buildFilter(LinkFilter filter) {
        String descriptionPattern = filter.getDescriptionContains();
        String urlPattern = filter.getUrlContains();
        Bson descriptionCondition = null;
        Bson urlCondition = null;
        if (!Strings.isNullOrEmpty(descriptionPattern)) {
            descriptionCondition = regex("description", ".*" + descriptionPattern + ".*", "i");
        }
        if (!Strings.isNullOrEmpty(urlPattern)) {
            urlCondition = regex("url", ".*" + urlPattern + ".*", "i");
        }
        if (descriptionCondition != null && urlCondition != null) {
            return and(descriptionCondition, urlCondition);
        }
        return descriptionCondition != null ? descriptionCondition : urlCondition;
    }
}
