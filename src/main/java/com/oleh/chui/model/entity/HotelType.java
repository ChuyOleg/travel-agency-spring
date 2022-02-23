package com.oleh.chui.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "hotel_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class HotelType {

    @Id
    @Column(name = "hotel_type_id")
    @SequenceGenerator(
            name = "hotel_type_sequence",
            sequenceName = "hotel_type_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hotel_type_sequence"
    )
    private Long id;

    @Column(name = "hotel_type")
    @Enumerated(EnumType.STRING)
    private HotelTypeEnum value;

    public enum HotelTypeEnum {
        ONE_STAR(1),
        TWO_STARS(2),
        THREE_STARS(3),
        FOUR_STARS(4),
        FIVE_STARS(5);

        private final int starNumber;

        HotelTypeEnum(int starNumber) {
            this.starNumber = starNumber;
        }

        public int getStarNumber() {
            return starNumber;
        }
    }

}
