package oa.countries.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountryConfig {
    @Value("${countries.url}")
    private String url;

    public String getUrl() {
        return url;
    }
}
