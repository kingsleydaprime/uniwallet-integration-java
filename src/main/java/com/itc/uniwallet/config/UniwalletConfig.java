package com.itc.uniwallet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "uniwallet")
public class UniwalletConfig {

    private String baseUrl;
    private String apiKey;
    private String country;
    private String transflowId;
    private String productId;
}
