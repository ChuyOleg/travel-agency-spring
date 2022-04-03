package com.oleh.chui.model.service;

import com.oleh.chui.model.dto.TourDto;
import com.oleh.chui.model.entity.HotelType;
import com.oleh.chui.model.entity.Tour;
import com.oleh.chui.model.entity.TourType;
import com.oleh.chui.model.exception.tour.TourNameIsReservedException;
import com.oleh.chui.model.repository.TourRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TourServiceTest {

    @MockBean
    private TourRepository tourRepository;
    @MockBean
    private CountryService countryService;
    @MockBean
    private CityService cityService;
    @MockBean
    private TourTypeService tourTypeService;
    @MockBean
    private HotelTypeService hotelTypeService;
    @Autowired
    private TourService tourService;

    private static final String TOUR_NAME = "NAME";

    private static final TourDto TOUR_DTO = new TourDto(
            TOUR_NAME,
            BigDecimal.valueOf(1300),
            "COUNTRY",
            "CITY",
            "DESCRIPTION",
            10,
            2,
            TourType.TourTypeEnum.VACATION.name(),
            HotelType.HotelTypeEnum.FOUR_STARS.name(),
            2,
            LocalDate.of(2022, 8, 2),
            LocalDate.of(2022, 8, 8),
            "BURNING"
    );

    private static final Tour TOUR = new Tour(TOUR_DTO);

    @Test
    void checkCreateShouldWorkWithoutException() {
        when(tourRepository.findByName(TOUR_NAME)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> tourService.create(TOUR_DTO));

        verify(tourRepository, times(1)).save(TOUR);
    }

    @Test
    void checkCreateShouldThrowTourNameIsReservedException() {
        when(tourRepository.findByName(TOUR_NAME)).thenReturn(Optional.of(TOUR));

        assertThrows(
                TourNameIsReservedException.class,
                () -> tourService.create(TOUR_DTO)
        );
    }

    @Test
    void checkUpdateShouldWorkWithoutException() {
        when(tourRepository.findById(1L)).thenReturn(Optional.of(TOUR));
        assertDoesNotThrow(() -> tourService.update(TOUR_DTO, 1L));

        verify(tourRepository, times(1)).save(TOUR);
    }

    @Test
    void changeBurningState() {
        when(tourRepository.findById(1L)).thenReturn(Optional.of(TOUR));

        tourService.changeBurningState(1L);
        verify(tourRepository, times(1)).save(TOUR);
    }

    @Test
    void checkDeleteTour() {
        tourService.delete(1L);
        verify(tourRepository, times(1)).deleteById(1L);
    }


}