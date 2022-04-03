package com.oleh.chui.controller.validator;

import com.oleh.chui.controller.exception.filter_parameter.InvalidMaxPriceException;
import com.oleh.chui.controller.exception.filter_parameter.InvalidMinPriceException;
import com.oleh.chui.controller.exception.filter_parameter.InvalidPersonNumberException;
import com.oleh.chui.controller.validator.util.FieldValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;

import static com.oleh.chui.controller.validator.alias.CatalogFilterExceptionNamesForRequest.*;

/**
 * Validate parameters [personNumber, minPrice, maxPrice]
 * for filtration on the Catalog page
 *
 * @author Oleh Chui
 */
@Log4j2
public class FilterParametersValidator {

    private FilterParametersValidator() {}

    /**
     * Validate [personNumber, minPrice, maxPrice]
     * and process exceptions in one place.
     *
     * @param personNumber String.
     * @param minPrice String.
     * @param maxPrice String.
     * @param model Model instance.
     * @return boolean representing are parameters valid or not
     */
    public static boolean validate(String personNumber, String minPrice, String maxPrice, Model model) {
        try {
            checkForValidPersonNumber(personNumber);
            checkForValidMinPrice(minPrice);
            checkForValidMaxPrice(maxPrice);
            return true;
        } catch (InvalidMaxPriceException e) {
            log.warn("<catalog filtration> maxPrice is invalid ({})", maxPrice);
            model.addAttribute(INVALID_MAX_PRICE, true);
        } catch (InvalidMinPriceException e) {
            log.warn("<catalog filtration> minPrice is invalid ({})", minPrice);
            model.addAttribute(INVALID_MIN_PRICE, true);
        } catch (InvalidPersonNumberException e) {
            log.warn("<catalog filtration> personNumber is invalid ({})", personNumber);
            model.addAttribute(INVALID_PERSON_NUMBER, true);
        }

        return false;
    }

    private static void checkForValidPersonNumber(String personNumber) throws InvalidPersonNumberException {
        if (!FieldValidator.fieldIsEmpty(personNumber) && FieldValidator.fieldIsNotValidInteger(personNumber)) {
            throw new InvalidPersonNumberException();
        }
    }

    private static void checkForValidMinPrice(String minPrice) throws InvalidMinPriceException {
        if (!FieldValidator.fieldIsEmpty(minPrice) && FieldValidator.fieldIsNotValidBigDecimal(minPrice)) {
            throw new InvalidMinPriceException();
        }
    }

    private static void checkForValidMaxPrice(String maxPrice) throws InvalidMaxPriceException {
        if (!FieldValidator.fieldIsEmpty(maxPrice) && FieldValidator.fieldIsNotValidBigDecimal(maxPrice)) {
            throw new InvalidMaxPriceException();
        }
    }

}
