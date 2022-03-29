package com.oleh.chui.model.entity;

import com.oleh.chui.model.dto.TourDto;
import com.oleh.chui.model.entity.constant.ColumnName;
import com.oleh.chui.model.entity.constant.SequenceName;
import com.oleh.chui.model.entity.constant.TableName;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;

@Entity
@Table(name = TableName.TOUR_TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"name"})
public class Tour {

    @Id
    @Column(name = ColumnName.TOUR_ID)
    @SequenceGenerator(
            name = SequenceName.TOUR_SEQUENCE_NAME,
            sequenceName = SequenceName.TOUR_SEQUENCE_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = SequenceName.TOUR_SEQUENCE_NAME
    )
    private Long id;

    @Column(name = ColumnName.NAME)
    private String name;

    @Column(name = ColumnName.PRICE)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = ColumnName.CITY_ID)
    private City city;

    @Column(name = ColumnName.DESCRIPTION, columnDefinition = "TEXT")
    private String description;

    @Column(name = ColumnName.MAX_DISCOUNT)
    private int maxDiscount;

    @Column(name = ColumnName.DISCOUNT_STEP)
    private double discountStep;

    @ManyToOne
    @JoinColumn(name = ColumnName.TOUR_TYPE_ID)
    private TourType tourType;

    @ManyToOne
    @JoinColumn(name = ColumnName.HOTEL_TYPE_ID)
    private HotelType hotelType;

    @Column(name = ColumnName.PERSON_NUMBER)
    private int personNumber;

    @Column(name = ColumnName.START_DATE)
    private LocalDate startDate;

    @Column(name = ColumnName.END_DATE)
    private LocalDate endDate;

    @Column(name = ColumnName.NIGHTS_NUMBER)
    private int nightsNumber;

    @Column(name = ColumnName.IS_BURNING)
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
