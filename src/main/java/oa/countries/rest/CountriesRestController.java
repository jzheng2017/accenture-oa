package oa.countries.rest;

import oa.countries.api.Country;
import oa.countries.api.CountryService;
import oa.countries.api.Filter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CountriesRestController {
    private CountryService countryService;

    public CountriesRestController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public List<Country> getCountries() {
        List<Country> countries = countryService.getCountries(new Filter(Filter.Region.ALL, List.of("name", "region", "population", "area", "borders", "cca3")));
        List<Country> asianCountries = countries
                .stream()
                .filter(country -> "asia".equalsIgnoreCase(country.region()))
                .toList();
        return countries;
    }
}
