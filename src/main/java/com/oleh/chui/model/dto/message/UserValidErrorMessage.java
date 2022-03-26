package com.oleh.chui.model.dto.message;

public class UserValidErrorMessage {

    private UserValidErrorMessage() {}

    public static final String USER_ERROR_IS_BLANK = "isBlank";
    public static final String USER_ERROR_SIZE_OUT_OF_BOUNDS = "sizeOutOfBounds";
    public static final String USER_ERROR_PASSWORD_NOT_MATCH_TEMPLATE = "passwordNotMatchTemplate";
    public static final String USER_ERROR_PASSWORDS_NOT_MATCH = "passwordsNotMatch";
    public static final String USER_ERROR_USERNAME_IS_RESERVED = "usernameIsReserved";

}
