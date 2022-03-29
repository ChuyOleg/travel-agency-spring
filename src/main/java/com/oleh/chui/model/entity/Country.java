package com.oleh.chui.model.entity;

import com.oleh.chui.model.entity.constant.ColumnName;
import com.oleh.chui.model.entity.constant.SequenceName;
import com.oleh.chui.model.entity.constant.TableName;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = TableName.COUNTRY_TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"citySet"})
@EqualsAndHashCode(exclude = {"id", "citySet"})
public class Country {

    @Id
    @Column(name = ColumnName.COUNTRY_ID)
    @SequenceGenerator(
            name = SequenceName.COUNTRY_SEQUENCE_NAME,
            sequenceName = SequenceName.COUNTRY_SEQUENCE_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = SequenceName.COUNTRY_SEQUENCE_NAME
    )
    private Long id;

    @Column(name = ColumnName.COUNTRY)
    private String country;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<City> citySet;

}
