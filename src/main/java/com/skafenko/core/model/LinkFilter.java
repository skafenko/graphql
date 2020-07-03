package com.skafenko.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkFilter {
    private String descriptionContains;
    private String urlContains;
}
