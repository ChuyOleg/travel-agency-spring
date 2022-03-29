package com.oleh.chui.controller.guest;

import com.oleh.chui.controller.util.HtmlPagePath;
import com.oleh.chui.controller.util.UriPath;
import com.oleh.chui.model.dto.UserDto;
import com.oleh.chui.model.exception.user.UsernameIsReservedException;
import com.oleh.chui.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.oleh.chui.controller.util.Attribute.*;
import static com.oleh.chui.model.dto.message.UserValidErrorMessage.*;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping(UriPath.REGISTRATION)
    public String getRegistrationPage(Model model) {
        model.addAttribute(USER_DTO, new UserDto());

        return HtmlPagePath.GUEST_REGISTRATION_PAGE;
    }

    @PostMapping(UriPath.REGISTRATION)
    public String createNewAccount(@ModelAttribute(name = USER_DTO) @Valid UserDto userDto,
                                   BindingResult validationResult,
                                   Model model) {

        if (!validationResult.hasFieldErrors(USER_DTO_FIELD_PASSWORD) && !userDto.getPassword().equals(userDto.getPasswordCopy())) {
            validationResult.addError(
                    new FieldError(UserDto.class.getName(), USER_DTO_FIELD_PASSWORD_COPY, USER_ERROR_PASSWORDS_NOT_MATCH)
            );
        }

        if (!validationResult.hasErrors()) {
            try {
                userService.registerNewAccount(userDto);

                return UriPath.REDIRECT + UriPath.LOGIN;
            } catch (UsernameIsReservedException e) {
                model.addAttribute(USER_ERROR_USERNAME_IS_RESERVED, true);
            }
        }

        return HtmlPagePath.GUEST_REGISTRATION_PAGE;
    }


}
