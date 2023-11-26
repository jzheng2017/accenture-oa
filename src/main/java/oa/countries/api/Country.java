package oa.countries.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public record Country(Name name, String cca3, String region, List<String> borders, long population, long area) {
    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    record Name(String common, String official) {}

    long populationDensity() {
        if (area <= 0) {
            LOGGER.warn("Can not compute population density of {}. Geographical size is {}.", name, area);
            return 0;
        }

        return population / area;
    }
}
