package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.City;
import com.oleh.chui.model.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getByCountryAndCity(String country, String city) {
        return cityRepository.findByCountry_CountryAndCity(country, city).orElseThrow(RuntimeException::new);
    }
}
