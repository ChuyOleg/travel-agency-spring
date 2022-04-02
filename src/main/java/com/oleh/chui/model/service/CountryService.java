package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.Country;
import com.oleh.chui.model.exception.city.CityNotExistException;
import com.oleh.chui.model.exception.country.CountryNotExistException;
import com.oleh.chui.model.repository.CountryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Log4j2
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void checkCountryAndCityExist(String country, String city) throws CountryNotExistException, CityNotExistException {
        Optional<Country> countryOptional = countryRepository.findByCountry(country);

        if (countryOptional.isPresent()) {
            boolean cityNotExists = countryOptional.get().getCitySet()
                    .stream()
                    .noneMatch(cityObj -> cityObj.getCity().equals(city));

            if (cityNotExists) {
                log.warn("City '{}' does not exist", city);
                throw new CityNotExistException();
            }
        } else {
            log.warn("Country '{}' does not exist", country);
            throw new CountryNotExistException();
        }
    }

}
