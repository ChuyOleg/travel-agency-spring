package com.oleh.chui.model.dto;

import com.oleh.chui.model.entity.Tour;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.oleh.chui.model.dto.restriction.TourRestriction.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TourDto {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = PRICE_MIN)
    @DecimalMax(value = PRICE_MAX)
    private BigDecimal price;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String description;

    @NotNull
    @Min(value = MAX_DISCOUNT_MIN)
    @Max(value = MAX_DISCOUNT_MAX)
    private int maxDiscount;

    @NotNull
    @Min(DISCOUNT_STEP_MIN)
    @Max(DISCOUNT_STEP_MAX)
    private double discountStep;

    @NotBlank
    private String tourType;

    @NotBlank
    private String hotelType;

    @NotNull
    @Min(value = PERSON_NUMBER_MIN)
    @Max(value = PERSON_NUMBER_MAX)
    private int personNumber = PERSON_NUMBER_DEFAULT_VALUE;

    @NotNull
    @DateTimeFormat(pattern = DATE_PATTERN)
    @Future
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate endDate;

    private String burning;

    public TourDto(Tour tour) {
        this.name = tour.getName();
        this.price = tour.getPrice();
        this.country = tour.getCity().getCountry().getCountry();
        this.city = tour.getCity().getCity();
        this.description = tour.getDescription();
        this.maxDiscount = tour.getMaxDiscount();
        this.discountStep = tour.getDiscountStep();
        this.tourType = tour.getTourType().getValue().name();
        this.hotelType = tour.getHotelType().getValue().name();
        this.personNumber = tour.getPersonNumber();
        this.startDate = tour.getStartDate();
        this.endDate = tour.getEndDate();
        this.burning = tour.isBurning() ? IS_BURNING_TRUE_VALUE : null;
    }

}
