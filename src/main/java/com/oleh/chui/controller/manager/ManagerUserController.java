package com.oleh.chui.controller.manager;

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

import static com.oleh.chui.controller.util.Attribute.*;

/**
 * Contains all possible actions for manager with user.
 *
 * @author Oleh Chui
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(UriPath.MANAGER_PREFIX)
public class ManagerUserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping(UriPath.USERS)
    public String getUsersPage(Model model) {
        List<User> userList = userService.getAllUsers();

        model.addAttribute(USER_LIST, userList);

        return HtmlPagePath.MANAGER_USERS_PAGE;
    }

    @GetMapping(UriPath.USER + UriPath.PATH_VARIABLE_ID)
    public String getUserAccountPage(@PathVariable(name = UriPath.ID) Long userId,
                              Model model) {

        User user = userService.getById(userId);
        List<Order> orderList = orderService.getAllByUserId(userId);

        model.addAttribute(USER, user);
        model.addAttribute(ORDER_LIST, orderList);

        return HtmlPagePath.MANAGER_USER_ACCOUNT_PAGE;
    }

}
