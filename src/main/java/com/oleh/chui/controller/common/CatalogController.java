package com.oleh.chui.controller.common;

import com.oleh.chui.controller.util.HtmlPagePath;
import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.controller.validator.FilterParametersValidator;
import com.oleh.chui.controller.validator.util.FieldValidator;
import com.oleh.chui.model.entity.HotelType;
import com.oleh.chui.model.entity.TourType;
import com.oleh.chui.model.service.TourService;
import com.oleh.chui.model.service.util.pagination.PaginationInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;

import static com.oleh.chui.controller.util.Attribute.*;

/**
 * Contains all interactions with Catalog page.
 *
 * @author Oleh Chui
 */
@Controller
@RequiredArgsConstructor
@Log4j2
public class CatalogController {

    private final TourService tourService;

    @GetMapping(UriPath.CATALOG)
    public String getCatalogPage(@RequestParam(name = PERSON_NUMBER, required = false) String personNumber,
                                 @RequestParam(name = MIN_PRICE, required = false) String minPrice,
                                 @RequestParam(name = MAX_PRICE, required = false) String maxPrice,
                                 @RequestParam(name = TOUR_TYPE, required = false) String[] tourTypeArray,
                                 @RequestParam(name = HOTEL_TYPE, required = false) String[] hotelTypeArray,
                                 @RequestParam(name = PAGE, required = false) String pageNumber,
                                 Model model) {

        boolean filterParamsAreValid = FilterParametersValidator.validate(personNumber, minPrice, maxPrice, model);

        if (filterParamsAreValid) {
            final int activePageNumber = getActivePageNumber(pageNumber);

            PaginationInfo paginationResultData = tourService.getPaginationResultData(
                    personNumber, minPrice, maxPrice, tourTypeArray, hotelTypeArray, activePageNumber
            );

            model.addAttribute(ACTIVE_PAGE_NUMBER, activePageNumber);
            model.addAttribute(PAGES_NUMBER , paginationResultData.getPagesNumber());
            model.addAttribute(TOUR_LIST, paginationResultData.getTourList());
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
        model.addAttribute(TOUR_TYPE_LIST, TourType.TourTypeEnum.values());
        model.addAttribute(HOTEL_TYPE_LIST, HotelType.HotelTypeEnum.values());
    }

    private void insertFilterParamsIntoModel(String personNumber, String minPrice, String maxPrice,
                                             String[] tourTypeArray, String[] hotelTypeArray, Model model) {

        model.addAttribute(PERSON_NUMBER, personNumber);
        model.addAttribute(MIN_PRICE, minPrice);
        model.addAttribute(MAX_PRICE, maxPrice);
        if (tourTypeArray != null) model.addAttribute(CHECKED_TOUR_TYPE_LIST, new ArrayList<>(Arrays.asList(tourTypeArray)));
        if (hotelTypeArray != null) model.addAttribute(CHECKED_HOTEL_TYPE_LIST, new ArrayList<>(Arrays.asList(hotelTypeArray)));
    }

}
