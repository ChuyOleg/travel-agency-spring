package com.oleh.chui.model.repository;

import com.oleh.chui.model.entity.TourType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourTypeRepository extends JpaRepository<TourType, Long> {

    Optional<TourType> findByValue(TourType.TourTypeEnum value);

}
