package com.oleh.chui.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tour_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class TourType {

    @Id
    @Column(name = "tour_type_id")
    @SequenceGenerator(
            name = "tour_type_sequence",
            sequenceName = "tour_type_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tour_type_sequence"
    )
    private Long id;

    @Column(name = "tour_type")
    @Enumerated(EnumType.STRING)
    private TourTypeEnum value;

    public enum TourTypeEnum {
        VACATION,
        EXCURSION,
        SHOPPING
    }

}
