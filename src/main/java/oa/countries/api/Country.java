package oa.countries.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public record Country(Name name, String cca3, String region, List<String> borders, long population, long area) {
    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    public record Name(String common, String official) {}

    public int populationDensity() {
        if (area <= 0) {
            LOGGER.warn("Can not compute population density of {}. Geographical size is {}.", name, area);
            return 0;
        }

        return (int) (population / area);
    }
}
