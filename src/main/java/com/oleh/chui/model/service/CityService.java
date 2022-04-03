package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.City;
import com.oleh.chui.model.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Manager business logic related with City.
 *
 * @author Oleh Chui
 */
@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City getByCountryAndCity(String country, String city) {
        return cityRepository.findByCountry_CountryAndCity(country, city).orElseThrow(RuntimeException::new);
    }
}
