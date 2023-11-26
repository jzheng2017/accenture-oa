package oa.countries.impl;

import oa.countries.api.Country;
import oa.countries.api.CountryHttpService;
import oa.countries.api.Filter;
import oa.countries.config.CountryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
public class SpringRestClientCountryHttpService implements CountryHttpService {
    private final CountryConfig countryConfig;
    private final Logger LOGGER = LoggerFactory.getLogger(SpringRestClientCountryHttpService.class);

    public SpringRestClientCountryHttpService(CountryConfig countryConfig) {
        this.countryConfig = countryConfig;
    }

    @Override
    public List<Country> getCountries(Filter filter) {
        try {
            RestClient client = RestClient.create();
            String url = getUrlWithFilters(filter);
            LOGGER.info("Requesting country information from {}", url);
            Country[] result = client
                    .get()
                    .uri(url)
                    .retrieve()
                    .body(Country[].class);

            return Arrays.stream(result).toList();
        } catch (Exception e) {
            LOGGER.error("Something went wrong while fetching country information!", e);
            throw e;
        }
    }


    private String getUrlWithFilters(Filter filter) {
        if (filter.isEmpty()) {
            return "";
        }

        StringBuilder url = new StringBuilder(countryConfig.getUrl());
        if (!Filter.Region.ALL.equals(filter.region())) {
            url.append("/region/%s".formatted(filter.region()));
        } else {
            url.append("/all");
        }

        List<String> desiredFields = filter.desiredFields();

        if (desiredFields != null && !desiredFields.isEmpty()) {
            url.append("?fields=").append(String.join(",", desiredFields));
        }

        return url.toString();
    }
}
