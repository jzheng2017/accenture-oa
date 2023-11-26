package oa.countries.rest;

import oa.countries.api.Country;
import oa.countries.api.CountryService;
import oa.countries.api.Filter;
import oa.countries.util.CountryUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountriesRestController {
    private final CountryService countryService;

    public CountriesRestController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public CountryUtil.CountryResponse getCountriesWithHighestPopulationDensityAndMostBorderingCountries() {
        List<Country> countries = countryService.getCountries(new Filter(Filter.Region.ALL, List.of("name", "region", "population", "area", "borders", "cca3")));

        return CountryUtil.getCountriesWithHighestPopulationDensityAndMostBorderingCountries(countries, "asia");
    }
}
