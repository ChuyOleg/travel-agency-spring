package com.oleh.chui.controller.user;

import com.oleh.chui.controller.util.Attribute;
import com.oleh.chui.controller.util.HtmlPagePath;
import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.model.entity.Order;
import com.oleh.chui.model.entity.Role;
import com.oleh.chui.model.entity.User;
import com.oleh.chui.model.service.OrderService;
import com.oleh.chui.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(UriPath.USER_PREFIX)
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @PostMapping(UriPath.TOUR_BUY)
    public String buy(@RequestParam Long tourId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.RoleEnum.USER.name()))) {
            User currentUser = (User) authentication.getPrincipal();
            Long userId = currentUser.getId();

            orderService.createOrder(userId, tourId);
        }

        return UriPath.REDIRECT + UriPath.CATALOG;
    }

    @GetMapping(UriPath.ACCOUNT)
    public String getAccountPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.RoleEnum.USER.name()))) {
            Long currentUserId = ((User) authentication.getPrincipal()).getId();

            User currentUser = userService.getById(currentUserId);
            List<Order> orderList = orderService.getAllByUserId(currentUserId);

            model.addAttribute(Attribute.USER, currentUser);
            model.addAttribute(Attribute.ORDER_LIST, orderList);
        }

        return HtmlPagePath.USER_ACCOUNT_PAGE;
    }

}
