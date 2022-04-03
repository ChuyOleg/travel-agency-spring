package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.City;
import com.oleh.chui.model.entity.Country;
import com.oleh.chui.model.exception.city.CityNotExistException;
import com.oleh.chui.model.exception.country.CountryNotExistException;
import com.oleh.chui.model.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CountryServiceTest {

    @MockBean
    private CountryRepository countryRepository;
    @Autowired
    private CountryService countryService;

    private static final String COUNTRY_STRING = "Vacanda";
    private static final String CITY_KALIV_STRING = "Kaliv";
    private static final String CITY_ORLEAN_STRING = "Orlean";
    private static final String CITY_DEKOR_STRING = "Dekor";

    private static final City CITY_KALIV = City.builder().city(CITY_KALIV_STRING).build();
    private static final City CITY_ORLEAN = City.builder().city(CITY_ORLEAN_STRING).build();
    private static final City CITY_DEKOR = City.builder().city(CITY_DEKOR_STRING).build();

    private static final Set<City> CITY_LIST_NOT_CONTAIN_KALIV = new HashSet<>(Arrays.asList(CITY_ORLEAN, CITY_DEKOR));
    private static final Set<City> CITY_LIST_CONTAIN_KALIV = new HashSet<>(Arrays.asList(CITY_ORLEAN, CITY_KALIV));

    @Test
    void testCheckCountryAndCityExistShouldNotThrowException() {
        Country country = new Country(1L, COUNTRY_STRING, CITY_LIST_CONTAIN_KALIV);
        when(countryRepository.findByCountry(COUNTRY_STRING)).thenReturn(Optional.of(country));

        assertDoesNotThrow(() -> countryService.checkCountryAndCityExist(COUNTRY_STRING, CITY_KALIV_STRING));
    }

    @Test
    void testCheckCountryAndCityExistShouldThrowCountryNotExistException() {
        when(countryRepository.findByCountry(COUNTRY_STRING)).thenReturn(Optional.empty());

        assertThrows(
                CountryNotExistException.class,
                () -> countryService.checkCountryAndCityExist(COUNTRY_STRING, CITY_KALIV_STRING)
        );
    }

    @Test
    void testCheckCountryAndCityExistShouldThrowCityNotExistException() {
        Country country = new Country(1L, COUNTRY_STRING, CITY_LIST_NOT_CONTAIN_KALIV);
        when(countryRepository.findByCountry(COUNTRY_STRING)).thenReturn(Optional.of(country));

        assertThrows(
                CityNotExistException.class,
                () -> countryService.checkCountryAndCityExist(COUNTRY_STRING, CITY_KALIV_STRING)
        );
    }

}