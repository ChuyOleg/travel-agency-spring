package com.oleh.chui.controller.validator.util;

import java.math.BigDecimal;

public class FieldValidator {

    private FieldValidator() {}

    public static boolean fieldIsEmpty(String field) {
        return field == null || field.trim().isEmpty();
    }

    public static boolean fieldIsNotValidInteger(String field) {
        try {
            Integer.parseInt(field);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    public static boolean fieldIsValidDouble(String field) {
        try {
            Double.parseDouble(field);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean fieldIsNotValidBigDecimal(String field) {
        try {
            new BigDecimal(field);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

}
