package oa.countries.api;

import java.util.List;

/**
 * An http service for retrieving information regarding countries
 */
public interface CountryHttpService {

    /**
     * Retrieve a list of countries information
     * @param filter filter object allowing filtering what is returned (e.g. certain fields)
     * @return a list of {@link Country}
     */
    List<Country> getCountries(Filter filter);
}
