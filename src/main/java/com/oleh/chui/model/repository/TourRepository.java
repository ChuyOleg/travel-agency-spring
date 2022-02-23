package com.oleh.chui.model.repository;

import com.oleh.chui.model.entity.HotelType;
import com.oleh.chui.model.entity.Tour;
import com.oleh.chui.model.entity.TourType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    Optional<Tour> findByName(String name);

    List<Tour> findAllByPersonNumberAndPriceGreaterThanEqualAndPriceLessThanEqualAndTourTypeInAndHotelTypeIn(
            int personNumber, BigDecimal minPrice, BigDecimal maxPrice,
            Collection<TourType> tourTypeCollection, Collection<HotelType> hotelTypeCollection
    );

}
