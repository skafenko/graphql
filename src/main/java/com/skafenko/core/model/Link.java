package com.skafenko.core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Link {
    private final String id;
    private final String url;
    private final String description;
    private final String userId;

    public Link(String url, String description, String userId) {
        id = UUID.randomUUID().toString();
        this.url = url;
        this.description = description;
        this.userId = userId;
    }
}
