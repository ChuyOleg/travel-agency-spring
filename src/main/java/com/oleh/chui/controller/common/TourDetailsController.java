package com.oleh.chui.controller.common;

import com.oleh.chui.controller.util.HtmlPagePath;
import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.model.entity.Role;
import com.oleh.chui.model.entity.Tour;
import com.oleh.chui.model.entity.User;
import com.oleh.chui.model.service.OrderService;
import com.oleh.chui.model.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

import static com.oleh.chui.controller.util.Attribute.*;

/**
 * Contains all interactions with TourDetails page.
 *
 * @author Oleh Chui
 */
@Controller
@RequiredArgsConstructor
public class TourDetailsController {

    private final TourService tourService;
    private final OrderService orderService;

    @GetMapping(UriPath.TOUR_DETAILS + UriPath.PATH_VARIABLE_ID)
    public String getTourDetailsPage(@PathVariable(name = UriPath.ID) Long id,
                                   Model model) {

        Tour selectedTour = tourService.getById(id);

        model.addAttribute(TOUR, selectedTour);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.RoleEnum.USER.name()))) {
            User currentUser = (User) authentication.getPrincipal();

            boolean tourIsBoughtByCurrentUser = orderService.isExistedByUserIdAndTourId(currentUser.getId(), id);

            if (tourIsBoughtByCurrentUser) {
                model.addAttribute(TOUR_IS_BOUGHT, true);
            } else {
                BigDecimal finalPrice = orderService.calculateFinalPrice(currentUser.getId(), id);
                model.addAttribute(FINAL_PRICE, finalPrice);
            }
        }


        return HtmlPagePath.COMMON_TOUR_DETAILS_PAGE;
    }

}
