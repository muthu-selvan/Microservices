package com.capturisk.loans.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "loans")
@Data
@ToString
public class LoansServiceConfig {
    private String message;
    private String buildVersion;
    private Map<String, String> mailDetails;
    private List<String> activeBranches;
}
