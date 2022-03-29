package com.oleh.chui.model.entity;

import com.oleh.chui.model.entity.constant.ColumnName;
import com.oleh.chui.model.entity.constant.SequenceName;
import com.oleh.chui.model.entity.constant.TableName;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = TableName.CITY_TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class City {

    @Id
    @Column(name = ColumnName.CITY_ID)
    @SequenceGenerator(
            name = SequenceName.CITY_SEQUENCE_NAME,
            sequenceName = SequenceName.CITY_SEQUENCE_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = SequenceName.CITY_SEQUENCE_NAME
    )
    private Long id;

    @Column(name = ColumnName.CITY)
    private String city;

    @ManyToOne
    @JoinColumn(name = ColumnName.COUNTRY_ID)
    private Country country;

}
