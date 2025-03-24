package com.mcq.loans.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "loans")
public record LoansConfigInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport,
                                 List<String> loanTypes, String paymentGracePeriod, String defaultPenalty) {
}
