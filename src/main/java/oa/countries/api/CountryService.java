package oa.countries.api;

import java.util.List;

/**
 * A service for retrieving country information
 */
public interface CountryService {

    /**
     * Retrieve a list of filtered country information
     * @return a list of country information
     */
    default List<Country> getCountries() {
        return getCountries(Filter.empty());
    }

    /**
     * Retrieve a list of filtered country information
     * @param filter filter object allowing filtering what is returned (e.g. certain fields)
     * @return a list of filtered {@link Country}
     */
    List<Country> getCountries(Filter filter);
}
