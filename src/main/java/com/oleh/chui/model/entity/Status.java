package com.oleh.chui.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "status")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class Status {

    @Id
    @Column(name = "status_id")
    @SequenceGenerator(
            name = "status_sequence",
            sequenceName = "status_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "status_sequence"
    )
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum value;

    public enum StatusEnum {
        REGISTERED,
        PAID,
        CANCELED
    }

    public Status(StatusEnum value) {
        this.value = value;
    }

}
