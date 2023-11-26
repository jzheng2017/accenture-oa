package oa.countries.util;

import oa.countries.api.Country;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CountryUtil {

    public static CountryResponse getCountriesWithHighestPopulationDensityAndMostBorderingCountries(List<Country> countries, String region) {
        List<Country> countriesInRegion = CountryUtil.getCountriesInRegion(region, countries);
        List<Country> countriesNotInRegion = CountryUtil.getCountriesNotInRegion(region, countries);
        Set<String> nonRegionCountryCodes = countriesNotInRegion.stream().map(Country::cca3).collect(Collectors.toSet());
        List<CountryResponse.CountryWithPopulationDensity> mostPopulousCountries = getPopulationWithHighestDensity(countries);
        List<CountryResponse.CountryMostBordering> mostBorderingCountries = getCountryWithMostBorderingCountriesOutsideOfRegion(countriesInRegion, nonRegionCountryCodes);

        return new CountryResponse(mostPopulousCountries, mostBorderingCountries);
    }

    private static List<Country> getCountriesInRegion(String region, List<Country> countries) {
        return countries
                .stream()
                .filter(country -> region.equalsIgnoreCase(country.region()))
                .toList();
    }

    private static List<Country> getCountriesNotInRegion(String region, List<Country> countries) {
        return countries
                .stream()
                .filter(country -> !region.equalsIgnoreCase(country.region()))
                .toList();
    }

    private static List<CountryResponse.CountryWithPopulationDensity> getPopulationWithHighestDensity(List<Country> countries) {
        return countries
                .stream()
                .map(country -> new CountryResponse.CountryWithPopulationDensity(country.name(), country.populationDensity()))
                .sorted(Collections.reverseOrder())
                .toList();
    }

    private static List<CountryResponse.CountryMostBordering> getCountryWithMostBorderingCountriesOutsideOfRegion(List<Country> countries, Set<String> countryCodeOutsideOfRegion) {
        return countries
                .stream()
                .map(country -> {
                            int numberOfBorderingCountries = 0;
                            for (String borderingCountry : country.borders()) {
                                if (countryCodeOutsideOfRegion.contains(borderingCountry)) {
                                    numberOfBorderingCountries++;
                                }
                            }
                            return new CountryResponse.CountryMostBordering(country.name(), numberOfBorderingCountries);
                        }
                )
                .sorted(Collections.reverseOrder())
                .toList();
    }

    public record CountryResponse(List<CountryWithPopulationDensity> density, List<CountryMostBordering> bordering) {
        public record CountryMostBordering(Country.Name name,
                                           int numberOfBorderingCountries) implements Comparable<CountryMostBordering> {
            @Override
            public int compareTo(CountryMostBordering o) {
                return numberOfBorderingCountries - o.numberOfBorderingCountries;
            }
        }

        public record CountryWithPopulationDensity(Country.Name name,
                                                   int populationDensity) implements Comparable<CountryWithPopulationDensity> {
            @Override
            public int compareTo(CountryWithPopulationDensity o) {
                return populationDensity - o.populationDensity;
            }
        }
    }
}
