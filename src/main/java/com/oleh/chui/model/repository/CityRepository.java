package com.oleh.chui.model.repository;

import com.oleh.chui.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByCountry_CountryAndCity(String country, String city);

}
