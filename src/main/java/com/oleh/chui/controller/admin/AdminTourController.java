package com.oleh.chui.controller.admin;

import com.oleh.chui.controller.util.Attribute;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(UriPath.ADMIN_PREFIX)
public class AdminTourController {

    private final TourService tourService;
    private final OrderService orderService;

    @GetMapping(UriPath.TOUR_CREATE)
    public String getCreateTourPage(Model model) {
        model.addAttribute(Attribute.TOUR_DTO, new TourDto());
        insertTourTypesAndHotelTypesIntoModel(model);

        return HtmlPagePath.ADMIN_CREATE_TOUR_PAGE;
    }

    @PostMapping(UriPath.TOUR_CREATE)
    public String createTour(@ModelAttribute(name = Attribute.TOUR_DTO) @Valid TourDto tourDto,
                             BindingResult validationResult,
                             Model model) {

        if (!validationResult.hasErrors()) {
            if (tourDto.getEndDate().isAfter(tourDto.getStartDate())) {
                try {
                    tourService.create(tourDto);
                    return UriPath.REDIRECT + UriPath.CATALOG;
                } catch (TourNameIsReservedException e) {
                    model.addAttribute(Attribute.NAME_IS_RESERVED, true);
                } catch (CityNotExistException e) {
                    model.addAttribute(Attribute.CITY_IS_UNDEFINED, true);
                } catch (CountryNotExistException e) {
                    model.addAttribute(Attribute.COUNTRY_IS_UNDEFINED, true);
                }
            } else {
                model.addAttribute(Attribute.INVALID_END_DATE, true);
            }
        }

        insertTourTypesAndHotelTypesIntoModel(model);
        return HtmlPagePath.ADMIN_CREATE_TOUR_PAGE;
    }

    @GetMapping(UriPath.TOUR + "/{id}")
    public String getUpdateTourPage(@PathVariable Long id, Model model) {
        TourDto tourDto = new TourDto(tourService.getById(id));

        model.addAttribute(Attribute.TOUR_DTO, tourDto);
        insertTourTypesAndHotelTypesIntoModel(model);
        model.addAttribute(Attribute.ID, id);

        return HtmlPagePath.ADMIN_UPDATE_TOUR_PAGE;
    }

    @PostMapping(UriPath.TOUR_UPDATE + "/{id}")
    public String updateTour(@PathVariable Long id,
                             @ModelAttribute(name = Attribute.TOUR_DTO) @Valid TourDto tourDto,
                             BindingResult validationResult,
                             Model model) {

        if (!validationResult.hasErrors()) {
            if (tourDto.getEndDate().isAfter(tourDto.getStartDate())) {
                try {
                    tourService.update(tourDto, id);
                    return UriPath.REDIRECT + UriPath.CATALOG;
                } catch (CityNotExistException e) {
                    model.addAttribute(Attribute.CITY_IS_UNDEFINED, true);
                } catch (CountryNotExistException e) {
                    model.addAttribute(Attribute.COUNTRY_IS_UNDEFINED, true);
                }
            } else {
                model.addAttribute(Attribute.INVALID_END_DATE, true);
            }
        }

        insertTourTypesAndHotelTypesIntoModel(model);
        model.addAttribute(Attribute.ID, id);
        return HtmlPagePath.ADMIN_UPDATE_TOUR_PAGE;
    }

    @PostMapping(UriPath.TOUR_DELETE)
    public String deleteTour(@RequestParam Long tourId) {
        boolean tourIsAlreadyBought = orderService.isExistedByTourId(tourId);

        if (tourIsAlreadyBought) {
            return UriPath.REDIRECT + UriPath.TOUR_DETAILS + UriPath.SLASH + tourId + Attribute.URL_ERROR_PARAMETER;
        }

        tourService.delete(tourId);

        return UriPath.REDIRECT + UriPath.CATALOG;
    }

    private void insertTourTypesAndHotelTypesIntoModel(Model model) {
        model.addAttribute(Attribute.TOUR_TYPE_LIST, TourType.TourTypeEnum.values());
        model.addAttribute(Attribute.HOTEL_TYPE_LIST, HotelType.HotelTypeEnum.values());
    }
}
