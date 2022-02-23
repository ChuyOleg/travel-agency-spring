package com.oleh.chui.model.repository;

import com.oleh.chui.model.entity.HotelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelTypeRepository extends JpaRepository<HotelType, Long> {

    Optional<HotelType> findByValue(HotelType.HotelTypeEnum value);

}
