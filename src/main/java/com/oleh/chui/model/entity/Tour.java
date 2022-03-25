package com.oleh.chui.model.entity;

import com.oleh.chui.model.dto.TourDto;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;

@Entity
@Table(name = "tours")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"name"})
public class Tour {

    @Id
    @Column(name = "tour_id")
    @SequenceGenerator(
            name = "tour_sequence",
            sequenceName = "tour_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tour_sequence"
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "max_discount")
    private int maxDiscount;

    @Column(name = "discount_step")
    private double discountStep;

    @ManyToOne
    @JoinColumn(name = "tour_type_id")
    private TourType tourType;

    @ManyToOne
    @JoinColumn(name = "hotel_type_id")
    private HotelType hotelType;

    @Column(name = "person_number")
    private int personNumber;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "nights_number")
    private int nightsNumber;

    @Column(name = "is_burning")
    private boolean burning;

    public Tour(TourDto tourDto) {
        Country country = Country.builder().country(tourDto.getCountry()).citySet(new HashSet<>()).build();
        City city = City.builder().city(tourDto.getCity()).country(country).build();
        TourType tourType = TourType.builder().value(TourType.TourTypeEnum.valueOf(tourDto.getTourType())).build();
        HotelType hotelType = HotelType.builder().value(HotelType.HotelTypeEnum.valueOf(tourDto.getHotelType())).build();

        this.name = tourDto.getName();
        this.price = tourDto.getPrice();
        this.city = city;
        this.description = tourDto.getDescription();
        this.maxDiscount = tourDto.getMaxDiscount();
        this.discountStep = tourDto.getDiscountStep();
        this.tourType = tourType;
        this.hotelType = hotelType;
        this.personNumber = tourDto.getPersonNumber();
        this.startDate = tourDto.getStartDate();
        this.endDate = tourDto.getEndDate();
        this.nightsNumber = (int) ChronoUnit.DAYS.between(this.startDate, this.endDate);
        this.burning = tourDto.getBurning() != null;
    }

}
