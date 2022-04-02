package com.oleh.chui.controller.util;

public class UriPath {

    private UriPath() {}

    public static final String REDIRECT = "redirect:";
    public static final String SLASH = "/";
    public static final String PATH_VARIABLE_ID = "/{id}";
    public static final String ID = "id";

    public static final String USER_PREFIX = "/user";
    public static final String MANAGER_PREFIX = "/manager";
    public static final String ADMIN_PREFIX = "/admin";

    public static final String USER = "/user";

    public static final String TOUR = "/tour";
    public static final String TOUR_CREATE = "/tour/create";
    public static final String TOUR_UPDATE = "/tour/update";
    public static final String TOUR_DELETE = "/tour/delete";
    public static final String TOUR_DETAILS = "/tour/details";
    public static final String TOUR_BUY = "/tour/buy";
    public static final String TOUR_CHANGE_BURNING_STATE = "/tour/changeBurningState";
    public static final String TOUR_CHANGE_DISCOUNT = "/tour/changeDiscount";

    public static final String ORDER_CHANGE_STATUS = "/order/changeStatus";

    public static final String ACCOUNT = "/account";

    public static final String USERS = "/users";

    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String REGISTRATION = "/registration";
    public static final String CATALOG = "/catalog";
}
