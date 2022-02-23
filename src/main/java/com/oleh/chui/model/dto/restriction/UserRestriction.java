package com.oleh.chui.model.dto.restriction;

public class UserRestriction {

    private UserRestriction() {}

    public static final int USERNAME_MIN_SIZE = 5;
    public static final int USERNAME_MAX_SIZE = 64;

    public static final int PASSWORD_MIN_SIZE = 8;
    public static final int PASSWORD_MAX_SIZE = 64;

    public static final int FIRST_NAME_MAX_SIZE = 32;

    public static final int LAST_NAME_MAX_SIZE = 32;

    public static final int EMAIL_MAX_SIZE = 128;

}
