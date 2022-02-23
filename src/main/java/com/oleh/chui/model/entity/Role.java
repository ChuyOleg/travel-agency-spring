package com.oleh.chui.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class Role {

    @Id
    @Column(name = "role_id")
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_sequence"
    )
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum value;

    public enum RoleEnum {
        UNKNOWN,
        USER,
        MANAGER,
        ADMIN
    }

}
