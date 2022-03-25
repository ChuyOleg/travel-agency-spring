package com.oleh.chui.controller.common;

import com.oleh.chui.controller.util.Attribute;
import com.oleh.chui.controller.util.HtmlPagePath;
import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.controller.validator.FilterParametersValidator;
import com.oleh.chui.controller.validator.util.FieldValidator;
import com.oleh.chui.model.entity.HotelType;
import com.oleh.chui.model.entity.Tour;
import com.oleh.chui.model.entity.TourType;
import com.oleh.chui.model.service.TourService;
import com.oleh.chui.model.service.util.filter.TourSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CatalogController {

    private final TourService tourService;
    private static final Integer PAGE_SIZE = 4;
    private static final Integer START_PAGE_NUMBER = 1;

    private static final String URI_PARAM_PAGE = "page";
    private static final String URI_PARAM_PERSON_NUMBER = "personNumber";
    private static final String URI_PARAM_MIN_PRICE = "minPrice";
    private static final String URI_PARAM_MAX_PRICE = "maxPrice";
    private static final String URI_PARAM_TOUR_TYPE = "tourType";
    private static final String URI_PARAM_HOTEL_TYPE = "hotelType";

    private static final String MODEL_PARAM_TOUR_LIST = "tourList";
    private static final String MODEL_PARAM_PAGES_NUMBER = "pagesNumber";
    private static final String MODEL_PARAM_ACTIVE_PAGE_NUMBER = "activePageNumber";
    private static final String MODEL_PARAM_CHECKED_TOUR_TYPE_LIST = "checkedTourTypeList";
    private static final String MODEL_PARAM_CHECKED_HOTEL_TYPE_LIST = "checkedHotelTypeList";

    @GetMapping(UriPath.CATALOG)
    public String getCatalogPage(@RequestParam(name = URI_PARAM_PERSON_NUMBER, required = false) String personNumber,
                                 @RequestParam(name = URI_PARAM_MIN_PRICE, required = false) String minPrice,
                                 @RequestParam(name = URI_PARAM_MAX_PRICE, required = false) String maxPrice,
                                 @RequestParam(name = URI_PARAM_TOUR_TYPE, required = false) String[] tourTypeArray,
                                 @RequestParam(name = URI_PARAM_HOTEL_TYPE, required = false) String[] hotelTypeArray,
                                 @RequestParam(name = URI_PARAM_PAGE, required = false) String pageNumber,
                                 Model model) {

        boolean filterParamsAreValid = FilterParametersValidator.validate(personNumber, minPrice, maxPrice, model);

        if (filterParamsAreValid) {
            final int activePageNumber = getActivePageNumber(pageNumber);

            TourSpecification tourSpecification = tourService.buildSpecification(personNumber, minPrice, maxPrice, tourTypeArray, hotelTypeArray);

            final int pagesNumber = tourService.getPagesCountBySpecification(tourSpecification, PAGE_SIZE);
            List<Tour> tourList = tourService.getPageBySpecification(tourSpecification, activePageNumber, PAGE_SIZE);

            model.addAttribute(MODEL_PARAM_ACTIVE_PAGE_NUMBER, activePageNumber);
            model.addAttribute(MODEL_PARAM_PAGES_NUMBER , pagesNumber);
            model.addAttribute(MODEL_PARAM_TOUR_LIST, tourList);
        }

        insertFilterParamsIntoModel(personNumber, minPrice, maxPrice, tourTypeArray, hotelTypeArray, model);
        insertTourTypesAndHotelTypesIntoModel(model);

        return HtmlPagePath.COMMON_CATALOG_PAGE;
    }

    private int getActivePageNumber(String pageNumber) {
        if (!FieldValidator.fieldIsEmpty(pageNumber)) {
            return Integer.parseInt(pageNumber);
        } else {
            return START_PAGE_NUMBER;
        }
    }

    private void insertTourTypesAndHotelTypesIntoModel(Model model) {
        model.addAttribute(Attribute.TOUR_TYPE_LIST, TourType.TourTypeEnum.values());
        model.addAttribute(Attribute.HOTEL_TYPE_LIST, HotelType.HotelTypeEnum.values());
    }

    private void insertFilterParamsIntoModel(String personNumber, String minPrice, String maxPrice,
                                             String[] tourTypeArray, String[] hotelTypeArray, Model model) {

        model.addAttribute(URI_PARAM_PERSON_NUMBER, personNumber);
        model.addAttribute(URI_PARAM_MIN_PRICE, minPrice);
        model.addAttribute(URI_PARAM_MAX_PRICE, maxPrice);
        if (tourTypeArray != null) model.addAttribute(MODEL_PARAM_CHECKED_TOUR_TYPE_LIST, new ArrayList<>(Arrays.asList(tourTypeArray)));
        if (hotelTypeArray != null) model.addAttribute(MODEL_PARAM_CHECKED_HOTEL_TYPE_LIST, new ArrayList<>(Arrays.asList(hotelTypeArray)));
    }

}
