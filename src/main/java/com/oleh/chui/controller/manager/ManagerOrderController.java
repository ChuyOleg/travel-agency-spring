package com.oleh.chui.controller.manager;

import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.model.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.oleh.chui.controller.util.Attribute.*;

/**
 * Contains all possible actions for manager with orders.
 *
 * @author Oleh Chui
 */
@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping(UriPath.MANAGER_PREFIX)
public class ManagerOrderController {

    private final OrderService orderService;

    @PostMapping(UriPath.ORDER_CHANGE_STATUS)
    public String changeOrderStatus(@RequestParam(name = USER_ID) Long userId,
                                    @RequestParam(name = ORDER_ID) Long orderId,
                                    @RequestParam(name = NEW_STATUS) String newStatus) {

        log.info("Manager (or Admin) wants to change status of the order (id = {})", orderId);

        orderService.changeStatus(newStatus, orderId);

        return UriPath.REDIRECT + UriPath.MANAGER_PREFIX + UriPath.USER + UriPath.SLASH + userId;
    }

}
