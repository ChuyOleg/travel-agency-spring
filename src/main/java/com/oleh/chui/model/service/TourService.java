package com.oleh.chui.model.service;

import com.oleh.chui.model.dto.TourDto;
import com.oleh.chui.model.entity.HotelType;
import com.oleh.chui.model.entity.Tour;
import com.oleh.chui.model.entity.TourType;
import com.oleh.chui.model.exception.city.CityNotExistException;
import com.oleh.chui.model.exception.country.CountryNotExistException;
import com.oleh.chui.model.exception.tour.TourNameIsReservedException;
import com.oleh.chui.model.service.util.filter.SearchCriteria;
import com.oleh.chui.model.service.util.filter.SearchOperation;
import com.oleh.chui.model.repository.TourRepository;
import com.oleh.chui.model.service.util.filter.TourSpecification;
import com.oleh.chui.model.service.util.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class TourService {

    private final TourRepository tourRepository;
    private final CountryService countryService;
    private final CityService cityService;
    private final TourTypeService tourTypeService;
    private final HotelTypeService hotelTypeService;

    private static final int PAGE_SIZE = 4;
    private static final String BURNING = "burning";
    private static final String ID = "id";
    private static final String START_DATE = "startDate";
    private static final String PERSON_NUMBER = "personNumber";
    private static final String PRICE = "price";
    private static final String TOUR_TYPE = "tourType";
    private static final String HOTEL_TYPE = "hotelType";
    private static final String TOUR_TYPE_VALUE = "value";
    private static final String HOTEL_TYPE_VALUE = "value";

    public Tour getById(Long id) {
        return tourRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional()
    public void create(TourDto tourDto) throws TourNameIsReservedException, CityNotExistException, CountryNotExistException {
        checkTourNameIsReserved(tourDto.getName());
        countryService.checkCountryAndCityExist(tourDto.getCountry(), tourDto.getCity());

        Tour tour = new Tour(tourDto);
        tour.setCity(cityService.getByCountryAndCity(tour.getCity().getCountry().getCountry(), tour.getCity().getCity()));
        tour.setTourType(tourTypeService.getByValue(tour.getTourType().getValue()));
        tour.setHotelType(hotelTypeService.getByValue(tour.getHotelType().getValue()));

        tourRepository.save(tour);
        log.info("New tour '{}' has been created", tour);
    }

    @Transactional()
    public void update(TourDto tourDto, Long id) throws CityNotExistException, CountryNotExistException {
        countryService.checkCountryAndCityExist(tourDto.getCountry(), tourDto.getCity());

        Tour tour = new Tour(tourDto);
        tour.setId(id);
        tour.setCity(cityService.getByCountryAndCity(tour.getCity().getCountry().getCountry(), tour.getCity().getCity()));
        tour.setTourType(tourTypeService.getByValue(tour.getTourType().getValue()));
        tour.setHotelType(hotelTypeService.getByValue(tour.getHotelType().getValue()));

        tourRepository.save(tour);
        log.info("Tour (id = {}) has been updated", id);
    }

    public void delete(Long id) {
        tourRepository.deleteById(id);
    }

    public void changeBurningState(Long id) {
        Tour tour = getById(id);
        tour.setBurning(!tour.isBurning());
        tourRepository.save(tour);
        log.info("Burning state of tour (id = {}) has been changed", id);
    }

    public void changeDiscount(TourDto tourDto, Long id) {
        Tour tour = getById(id);
        tour.setMaxDiscount(tourDto.getMaxDiscount());
        tour.setDiscountStep(tourDto.getDiscountStep());
        tourRepository.save(tour);
        log.info("Discount of tour (id = {}) has been changed", id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public PaginationInfo getPaginationResultData(String personNumber,
                                                  String minPrice,
                                                  String maxPrice,
                                                  String[] tourTypeArray,
                                                  String[] hotelTypeArray,
                                                  int uiPageNumber) {

        PaginationInfo paginationResultData = new PaginationInfo();

        TourSpecification tourSpecification = buildSpecification(personNumber, minPrice, maxPrice, tourTypeArray, hotelTypeArray);

        List<Tour> pageOfTours = getPageBySpecification(tourSpecification, uiPageNumber);
        final int pagesNumber = getPagesCountBySpecification(tourSpecification);

        paginationResultData.setTourList(pageOfTours);
        paginationResultData.setPagesNumber(pagesNumber);

        return paginationResultData;
    }

    private List<Tour> getPageBySpecification(TourSpecification specification, int uiPageNumber) {
        final int dbPageNumber = uiPageNumber - 1;
        Pageable pageRequestWithBurningFirst = PageRequest.of(dbPageNumber, PAGE_SIZE, Sort.by(BURNING, ID).descending());

        return tourRepository.findAll(specification, pageRequestWithBurningFirst).toList();
    }

    private int getPagesCountBySpecification(TourSpecification specification) {
        final long toursCount = tourRepository.count(specification);

        return (int) Math.ceil((double) toursCount / PAGE_SIZE);
    }

    private TourSpecification buildSpecification(String personNumber,
                                                String minPrice,
                                                String maxPrice,
                                                String[] tourTypeArray,
                                                String[] hotelTypeArray) {

        TourSpecification tourSpecification = new TourSpecification();
        tourSpecification.add(new SearchCriteria(START_DATE, LocalDate.now(), SearchOperation.DATE_AFTER_THAN));

        if (personNumber != null && !personNumber.isEmpty()) {
            tourSpecification.add(new SearchCriteria(PERSON_NUMBER, personNumber, SearchOperation.EQUAL));
        }
        if (minPrice != null && !minPrice.isEmpty()) {
            tourSpecification.add(new SearchCriteria(PRICE, minPrice, SearchOperation.GREATER_THAN_EQUAL));
        }
        if (maxPrice != null && !maxPrice.isEmpty()) {
            tourSpecification.add(new SearchCriteria(PRICE, maxPrice, SearchOperation.LESS_THAN_EQUAL));
        }
        if (tourTypeArray != null) {
            List<TourType.TourTypeEnum> tourTypeEnumList = Arrays.stream(tourTypeArray)
                    .map(TourType.TourTypeEnum::valueOf)
                    .collect(Collectors.toList());
            tourSpecification.add(new SearchCriteria(TOUR_TYPE, tourTypeEnumList, SearchOperation.IN, TOUR_TYPE_VALUE));
        }
        if (hotelTypeArray != null) {
            List<HotelType.HotelTypeEnum> hotelTypeEnumList = Arrays.stream(hotelTypeArray)
                    .map(HotelType.HotelTypeEnum::valueOf)
                    .collect(Collectors.toList());
            tourSpecification.add(new SearchCriteria(HOTEL_TYPE, hotelTypeEnumList, SearchOperation.IN, HOTEL_TYPE_VALUE));
        }

        return tourSpecification;
    }

    private void checkTourNameIsReserved(String name) throws TourNameIsReservedException {
        Optional<Tour> optionalTour = tourRepository.findByName(name);

        if (optionalTour.isPresent()) {
            throw new TourNameIsReservedException();
        }
    }
}
