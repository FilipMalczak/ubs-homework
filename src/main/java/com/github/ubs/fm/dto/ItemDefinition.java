package com.github.ubs.fm.dto;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class ItemDefinition {
    String name;
    @Singular
    Set<ItemPricing> prices;
}
