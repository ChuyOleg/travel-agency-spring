package com.oleh.chui.controller.manager;

import com.oleh.chui.controller.util.Attribute;
import com.oleh.chui.controller.util.HtmlPagePath;
import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.model.entity.Order;
import com.oleh.chui.model.entity.User;
import com.oleh.chui.model.service.OrderService;
import com.oleh.chui.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(UriPath.MANAGER_PREFIX)
public class ManagerUserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping(UriPath.USERS)
    public String getUsersPage(Model model) {
        List<User> userList = userService.getAllUsers();

        model.addAttribute(Attribute.USER_LIST, userList);

        return HtmlPagePath.MANAGER_USERS_PAGE;
    }

    @GetMapping(UriPath.USER + "/{id}")
    public String getUserAccountPage(@PathVariable(name = "id") Long userId,
                              Model model) {

        User user = userService.getById(userId);
        List<Order> orderList = orderService.getAllByUserId(userId);

        model.addAttribute(Attribute.USER, user);
        model.addAttribute(Attribute.ORDER_LIST, orderList);

        return HtmlPagePath.MANAGER_USER_ACCOUNT_PAGE;
    }

}
