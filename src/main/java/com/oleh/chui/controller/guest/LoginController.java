package com.oleh.chui.controller.guest;

import com.oleh.chui.controller.util.HtmlPagePath;
import com.oleh.chui.controller.util.UriPath;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(UriPath.LOGIN)
    public String getLoginPage() {
        return HtmlPagePath.GUEST_LOGIN_PAGE;
    }

}
