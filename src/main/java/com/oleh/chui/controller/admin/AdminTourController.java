package com.oleh.chui.controller.admin;

import com.oleh.chui.controller.util.HtmlPagePath;
import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.model.dto.TourDto;
import com.oleh.chui.model.entity.HotelType;
import com.oleh.chui.model.entity.TourType;
import com.oleh.chui.model.exception.city.CityNotExistException;
import com.oleh.chui.model.exception.country.CountryNotExistException;
import com.oleh.chui.model.exception.tour.TourNameIsReservedException;
import com.oleh.chui.model.service.OrderService;
import com.oleh.chui.model.service.TourService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.oleh.chui.controller.util.Attribute.*;


/**
 * Contains all possible actions for admin with tour.
 *
 * @author Oleh Chui
 */
@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping(UriPath.ADMIN_PREFIX)
public class AdminTourController {

    private final TourService tourService;
    private final OrderService orderService;

    /**
     * @param model Model instance.
     * @return String representing path to createTourPage.html.
     */
    @GetMapping(UriPath.TOUR_CREATE)
    public String getCreateTourPage(Model model) {
        model.addAttribute(TOUR_DTO, new TourDto());
        insertTourTypesAndHotelTypesIntoModel(model);

        return HtmlPagePath.ADMIN_CREATE_TOUR_PAGE;
    }

    /**
     * @param tourDto TourDto instance from Thymeleaf.
     * @param validationResult BindingResult instance representing result of validation tourDto.
     * @param model Model instance.
     * @return String representing Uri path to Catalog page if success or path to createTourPage.html if errors.
     */
    @PostMapping(UriPath.TOUR_CREATE)
    public String createTour(@ModelAttribute(name = TOUR_DTO) @Valid TourDto tourDto,
                             BindingResult validationResult,
                             Model model) {

        if (!validationResult.hasErrors()) {
            if (tourDto.getEndDate().isAfter(tourDto.getStartDate())) {
                try {
                    log.info("Admin wants to create new Tour");
                    tourService.create(tourDto);
                    log.info("Admin successfully has created new tour");
                    return UriPath.REDIRECT + UriPath.CATALOG;
                } catch (TourNameIsReservedException e) {
                    log.warn("Name of tour '{}' is reserved", tourDto.getName());
                    model.addAttribute(NAME_IS_RESERVED, true);
                } catch (CityNotExistException e) {
                    log.warn("City of tour '{}' is undefined", tourDto.getCity());
                    model.addAttribute(CITY_IS_UNDEFINED, true);
                } catch (CountryNotExistException e) {
                    log.warn("Country of tour '{}' is undefined", tourDto.getCountry());
                    model.addAttribute(COUNTRY_IS_UNDEFINED, true);
                }
            } else {
                model.addAttribute(INVALID_END_DATE, true);
            }
        }

        insertTourTypesAndHotelTypesIntoModel(model);
        return HtmlPagePath.ADMIN_CREATE_TOUR_PAGE;
    }

    /**
     * @param id Long representing id of selected tour.
     * @param model Model instance.
     * @return String representing path to updateTourPage.html.
     */
    @GetMapping(UriPath.TOUR + UriPath.PATH_VARIABLE_ID)
    public String getUpdateTourPage(@PathVariable(name = UriPath.ID) Long id, Model model) {
        TourDto tourDto = new TourDto(tourService.getById(id));

        model.addAttribute(TOUR_DTO, tourDto);
        insertTourTypesAndHotelTypesIntoModel(model);
        model.addAttribute(ID, id);

        return HtmlPagePath.ADMIN_UPDATE_TOUR_PAGE;
    }

    /**
     * @param id Long representing id of tour.
     * @param tourDto TourDto instance.
     * @param validationResult BindingResult instance.
     * @param model Model instance.
     * @return String: UriPath to Catalog page if success | path to updateTourPage.html if errors.
     */
    @PostMapping(UriPath.TOUR_UPDATE + UriPath.PATH_VARIABLE_ID)
    public String updateTour(@PathVariable(name = UriPath.ID) Long id,
                             @ModelAttribute(name = TOUR_DTO) @Valid TourDto tourDto,
                             BindingResult validationResult,
                             Model model) {

        log.info("Admin wants to update tour (id = {})", id);
        if (!validationResult.hasErrors()) {
            if (tourDto.getEndDate().isAfter(tourDto.getStartDate())) {
                try {
                    tourService.update(tourDto, id);
                    return UriPath.REDIRECT + UriPath.CATALOG;
                } catch (CityNotExistException e) {
                    model.addAttribute(CITY_IS_UNDEFINED, true);
                } catch (CountryNotExistException e) {
                    model.addAttribute(COUNTRY_IS_UNDEFINED, true);
                }
            } else {
                model.addAttribute(INVALID_END_DATE, true);
            }
        }

        insertTourTypesAndHotelTypesIntoModel(model);
        model.addAttribute(ID, id);
        return HtmlPagePath.ADMIN_UPDATE_TOUR_PAGE;
    }

    /**
     * @param tourId Long representing id of Tour.
     * @return String: Uri path to Catalog page if success | Uri path to tourDetails page if errors.
     */
    @PostMapping(UriPath.TOUR_DELETE)
    public String deleteTour(@RequestParam Long tourId) {
        boolean tourIsAlreadyBought = orderService.isExistedByTourId(tourId);
        log.info("Admin wants to delete tour (id = {})", tourId);

        if (tourIsAlreadyBought) {
            log.warn("Tour (id = {}) cannot be deleted because it is already booked", tourId);
            return UriPath.REDIRECT + UriPath.TOUR_DETAILS + UriPath.SLASH + tourId + URL_ERROR_PARAMETER;
        }

        tourService.delete(tourId);
        log.warn("Tour (id = {}) has been deleted", tourId);

        return UriPath.REDIRECT + UriPath.CATALOG;
    }

    private void insertTourTypesAndHotelTypesIntoModel(Model model) {
        model.addAttribute(TOUR_TYPE_LIST, TourType.TourTypeEnum.values());
        model.addAttribute(HOTEL_TYPE_LIST, HotelType.HotelTypeEnum.values());
    }
}
