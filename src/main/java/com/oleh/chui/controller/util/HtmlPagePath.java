package com.oleh.chui.controller.util;

public class HtmlPagePath {

    private HtmlPagePath() {}

    public static final String COMMON_PREFIX = "common";
    public static final String GUEST_PREFIX = "guest";
    public static final String USER_PREFIX = "user";
    public static final String MANAGER_PREFIX = "manager";
    public static final String ADMIN_PREFIX = "admin";

    public static final String COMMON_CATALOG_PAGE = COMMON_PREFIX + "/catalogPage";
    public static final String COMMON_TOUR_DETAILS_PAGE = COMMON_PREFIX + "/tourDetailsPage";

    public static final String GUEST_LOGIN_PAGE = GUEST_PREFIX + "/loginPage";
    public static final String GUEST_REGISTRATION_PAGE = GUEST_PREFIX + "/registrationPage";

    public static final String USER_ACCOUNT_PAGE = USER_PREFIX + "/accountPage";

    public static final String MANAGER_CHANGE_DISCOUNT_PAGE = MANAGER_PREFIX + "/changeDiscountPage";
    public static final String MANAGER_USER_ACCOUNT_PAGE = MANAGER_PREFIX + "/userAccountPage";
    public static final String MANAGER_USERS_PAGE = MANAGER_PREFIX + "/usersPage";

    public static final String ADMIN_CREATE_TOUR_PAGE = ADMIN_PREFIX + "/createTourPage";
    public static final String ADMIN_UPDATE_TOUR_PAGE = ADMIN_PREFIX + "/updateTourPage";

}
