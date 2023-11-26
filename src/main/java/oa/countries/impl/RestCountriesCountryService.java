package oa.countries.impl;

import oa.countries.api.Country;
import oa.countries.api.CountryHttpService;
import oa.countries.api.CountryService;
import oa.countries.api.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RestCountriesCountryService implements CountryService {
    private final Logger LOGGER = LoggerFactory.getLogger(RestCountriesCountryService.class);
    private final CountryHttpService countryHttpService;
    private final CacheManager cacheManager;

    public RestCountriesCountryService(CountryHttpService countryHttpService, CacheManager cacheManager) {
        this.countryHttpService = countryHttpService;
        this.cacheManager = cacheManager;
    }

    @Cacheable("getCountries")
    @Override
    public List<Country> getCountries(Filter filter) {
        LOGGER.info("Fetching countries information with filter: {}", filter);
        return countryHttpService.getCountries(filter);
    }

    /**
     * Country information rarely change, so we can easily cache them for a day and probably even longer but to be on the safe side we clear them every day.
     */
    @Scheduled(fixedRate = 1L, timeUnit = TimeUnit.DAYS)
    public void evictCache() {
        Cache cache = cacheManager.getCache("getCountries");
        if (cache != null) {
            cache.clear();
            LOGGER.info("Cache store '{}' has been cleared", cache.getName());
        }
    }
}
