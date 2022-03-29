package com.oleh.chui.controller.manager;

import com.oleh.chui.controller.util.HtmlPagePath;
import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.model.dto.TourDto;
import com.oleh.chui.model.entity.Tour;
import com.oleh.chui.model.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.oleh.chui.controller.util.Attribute.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(UriPath.MANAGER_PREFIX)
public class ManagerTourController {

    private final TourService tourService;

    @PostMapping(UriPath.TOUR_CHANGE_BURNING_STATE)
    public String changeBurningState(@RequestParam Long tourId) {
        tourService.changeBurningState(tourId);

        return UriPath.REDIRECT + UriPath.TOUR_DETAILS + UriPath.SLASH + tourId;
    }

    @GetMapping(UriPath.TOUR_CHANGE_DISCOUNT + UriPath.PATH_VARIABLE_ID)
    public String getChangeDiscount(@PathVariable(name = UriPath.ID) Long id,
                                    Model model) {

        Tour tour = tourService.getById(id);

        model.addAttribute(TOUR_DTO, new TourDto(tour));
        model.addAttribute(ID, id);

        return HtmlPagePath.MANAGER_CHANGE_DISCOUNT_PAGE;
    }

    @PostMapping(UriPath.TOUR_CHANGE_DISCOUNT + UriPath.PATH_VARIABLE_ID)
    public String changeDiscount(@PathVariable(name = UriPath.ID) Long id,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 @ModelAttribute(name = TOUR_DTO) @Valid TourDto tourDto,
                                 BindingResult bindingResult
                                 ) {

        model.addAttribute(ID, id);

        if (bindingResult.hasFieldErrors(TOUR_DTO_DISCOUNT_STEP) || bindingResult.hasFieldErrors(TOUR_DTO_MAX_DISCOUNT)) {
            return HtmlPagePath.MANAGER_CHANGE_DISCOUNT_PAGE;
        }

        tourService.changeDiscount(tourDto, id);

        redirectAttributes.addFlashAttribute(DISCOUNT_HAS_BEEN_CHANGES, true);
        return UriPath.REDIRECT + UriPath.MANAGER_PREFIX + UriPath.TOUR_CHANGE_DISCOUNT + UriPath.SLASH + id;
    }

}
