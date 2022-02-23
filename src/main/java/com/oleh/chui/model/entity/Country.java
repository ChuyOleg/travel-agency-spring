package com.oleh.chui.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"citySet"})
@EqualsAndHashCode(exclude = {"id", "citySet"})
public class Country {

    @Id
    @Column(name = "country_id")
    @SequenceGenerator(
            name = "country_sequence",
            sequenceName = "country_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "country_sequence"
    )
    private Long id;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<City> citySet;

}
