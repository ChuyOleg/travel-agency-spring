package com.oleh.chui.model.entity;

import com.oleh.chui.model.entity.constant.ColumnName;
import com.oleh.chui.model.entity.constant.SequenceName;
import com.oleh.chui.model.entity.constant.TableName;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = TableName.TOUR_TYPE_TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class TourType {

    @Id
    @Column(name = ColumnName.TOUR_TYPE_ID)
    @SequenceGenerator(
            name = SequenceName.TOUR_TYPE_SEQUENCE_NAME,
            sequenceName = SequenceName.TOUR_TYPE_SEQUENCE_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = SequenceName.TOUR_TYPE_SEQUENCE_NAME
    )
    private Long id;

    @Column(name = ColumnName.TOUR_TYPE)
    @Enumerated(EnumType.STRING)
    private TourTypeEnum value;

    public enum TourTypeEnum {
        VACATION,
        EXCURSION,
        SHOPPING
    }

}
