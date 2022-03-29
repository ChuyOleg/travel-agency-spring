package com.oleh.chui.model.entity;

import com.oleh.chui.model.entity.constant.ColumnName;
import com.oleh.chui.model.entity.constant.SequenceName;
import com.oleh.chui.model.entity.constant.TableName;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = TableName.HOTEL_TYPE_TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class HotelType {

    @Id
    @Column(name = ColumnName.HOTEL_TYPE_ID)
    @SequenceGenerator(
            name = SequenceName.HOTEL_TYPE_SEQUENCE_NAME,
            sequenceName = SequenceName.HOTEL_TYPE_SEQUENCE_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = SequenceName.HOTEL_TYPE_SEQUENCE_NAME
    )
    private Long id;

    @Column(name = ColumnName.HOTEL_TYPE)
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
