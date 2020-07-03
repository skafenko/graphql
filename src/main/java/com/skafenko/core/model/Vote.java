package com.skafenko.core.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Vote {
    private final String id;
    private final ZonedDateTime createdAt;
    private final String userId;
    private final String linkId;
}
