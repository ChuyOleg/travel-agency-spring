package com.oleh.chui.controller.admin;

import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.oleh.chui.controller.util.Attribute.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(UriPath.ADMIN_PREFIX)
public class AdminUserController {

    private final UserService userService;

    @PostMapping(UriPath.USERS)
    public String blockOrUnblockUser(@RequestParam(name = ID) Long userId,
                                     @RequestParam(name = IS_BLOCKED) boolean isBlocked) {

        if (isBlocked) {
            userService.unblockById(userId);
        } else {
            userService.blockById(userId);
        }

        return UriPath.REDIRECT + UriPath.MANAGER_PREFIX + UriPath.USERS;
    }

}
