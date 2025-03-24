package com.mcq.cards.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "cards")
public record CardsConfigInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport,
                                 String creditLineLimitPolicy, Map<String, Double> interestRates, List<String> perks) {
}
