package com.oleh.chui.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.oleh.chui.model.dto.restriction.UserRestriction.*;
import static com.oleh.chui.model.dto.regexp.UserRegExp.*;
import static com.oleh.chui.model.dto.message.UserValidErrorMessage.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {

    @NotBlank
    @Size(min = USERNAME_MIN_SIZE, max = USERNAME_MAX_SIZE)
    private String username;

    @NotBlank(message = USER_ERROR_IS_BLANK)
    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE, message = USER_ERROR_SIZE_OUT_OF_BOUNDS)
    @Pattern(regexp = PASSWORD, message = USER_ERROR_PASSWORD_NOT_MATCH_TEMPLATE)
    private String password;

    private String passwordCopy;

    @NotBlank
    @Size(max = FIRST_NAME_MAX_SIZE)
    private String firstName;

    @NotBlank
    @Size(max = LAST_NAME_MAX_SIZE)
    private String lastName;

    @NotBlank
    @Size(max = EMAIL_MAX_SIZE)
    @Pattern(regexp = EMAIL)
    private String email;

}
