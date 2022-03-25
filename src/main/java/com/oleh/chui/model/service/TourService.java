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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;
    private final CountryService countryService;
    private final CityService cityService;
    private final TourTypeService tourTypeService;
    private final HotelTypeService hotelTypeService;

    @Transactional
    public void create(TourDto tourDto) throws TourNameIsReservedException, CityNotExistException, CountryNotExistException {
        checkTourNameIsReserved(tourDto.getName());
        countryService.checkCountryAndCityExist(tourDto.getCountry(), tourDto.getCity());

        Tour tour = new Tour(tourDto);
        tour.setCity(cityService.getByCountryAndCity(tour.getCity().getCountry().getCountry(), tour.getCity().getCity()));
        tour.setTourType(tourTypeService.getByValue(tour.getTourType().getValue()));
        tour.setHotelType(hotelTypeService.getByValue(tour.getHotelType().getValue()));

        tourRepository.save(tour);
    }

    public void update(TourDto tourDto, Long id) throws CityNotExistException, CountryNotExistException {
        countryService.checkCountryAndCityExist(tourDto.getCountry(), tourDto.getCity());

        Tour tour = new Tour(tourDto);
        tour.setId(id);
        tour.setCity(cityService.getByCountryAndCity(tour.getCity().getCountry().getCountry(), tour.getCity().getCity()));
        tour.setTourType(tourTypeService.getByValue(tour.getTourType().getValue()));
        tour.setHotelType(hotelTypeService.getByValue(tour.getHotelType().getValue()));

        tourRepository.save(tour);
    }

    public Tour getById(Long id) {
        return tourRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    private void checkTourNameIsReserved(String name) throws TourNameIsReservedException {
        Optional<Tour> optionalTour = tourRepository.findByName(name);

        if (optionalTour.isPresent()) {
            throw new TourNameIsReservedException();
        }
    }

    public List<Tour> getPageBySpecification(TourSpecification specification, int uiPageNumber, int pageSize) {
        final int dbPageNumber = uiPageNumber - 1;
        Pageable pageRequestWithBurningFirst = PageRequest.of(dbPageNumber, pageSize, Sort.by("burning").descending());

        return tourRepository.findAll(specification, pageRequestWithBurningFirst).toList();
    }

    public int getPagesCountBySpecification(TourSpecification specification, int pageSize) {
        final long toursCount = tourRepository.count(specification);

        return (int) Math.ceil((double) toursCount / pageSize);
    }

    public TourSpecification buildSpecification(String personNumber,
                                                String minPrice,
                                                String maxPrice,
                                                String[] tourTypeArray,
                                                String[] hotelTypeArray) {

        TourSpecification tourSpecification = new TourSpecification();
        tourSpecification.add(new SearchCriteria("startDate", LocalDate.now(), SearchOperation.DATE_AFTER_THAN));

        if (personNumber != null && !personNumber.isEmpty()) {
            tourSpecification.add(new SearchCriteria("personNumber", personNumber, SearchOperation.EQUAL));
        }
        if (minPrice != null && !minPrice.isEmpty()) {
            tourSpecification.add(new SearchCriteria("price", minPrice, SearchOperation.GREATER_THAN_EQUAL));
        }
        if (maxPrice != null && !maxPrice.isEmpty()) {
            tourSpecification.add(new SearchCriteria("price", maxPrice, SearchOperation.LESS_THAN_EQUAL));
        }
        if (tourTypeArray != null) {
            List<TourType.TourTypeEnum> tourTypeEnumList = Arrays.stream(tourTypeArray)
                    .map(TourType.TourTypeEnum::valueOf)
                    .collect(Collectors.toList());
            tourSpecification.add(new SearchCriteria("tourType", tourTypeEnumList, SearchOperation.IN, "value"));
        }
        if (hotelTypeArray != null) {
            List<HotelType.HotelTypeEnum> hotelTypeEnumList = Arrays.stream(hotelTypeArray)
                    .map(HotelType.HotelTypeEnum::valueOf)
                    .collect(Collectors.toList());
            tourSpecification.add(new SearchCriteria("hotelType", hotelTypeEnumList, SearchOperation.IN, "value"));
        }

        return tourSpecification;
    }

    // TODO: add Transactional
//    public List<Tour> getAllUsingFilter(
//            Optional<String> personNumberString,
//            Optional<String> minPriceString,
//            Optional<String> maxPriceString,
//            Optional<String[]> tourTypeArrOptional,
//            Optional<String[]> hotelTypeArrOptional) {
//
//        Set<TourType> tourTypeArr = tourTypeArrOptional.map(strings -> Arrays.stream(strings)
//                .map(tourType -> TourType.builder().value(TourType.TourTypeEnum.valueOf(tourType)).build())
//                .collect(Collectors.toSet())).orElseGet(HashSet::new);
//
//        Set<HotelType> hotelTypeArr = hotelTypeArrOptional.map(strings -> Arrays.stream(strings)
//                .map(hotelType -> HotelType.builder().value(HotelType.HotelTypeEnum.valueOf(hotelType)).build())
//                .collect(Collectors.toSet())).orElseGet(HashSet::new);
//
//        return tourRepository.findAllByPersonNumberAndPriceGreaterThanEqualAndPriceLessThanEqualAndTourTypeInAndHotelTypeIn(
//                Integer.parseInt(personNumberString.orElse("2")),
//                BigDecimal.valueOf(Double.parseDouble(minPriceString.orElse("0"))),
//                BigDecimal.valueOf(Double.parseDouble(maxPriceString.orElse("10000000"))),
//                tourTypeArr,
//                hotelTypeArr
//        );
//    }
}
