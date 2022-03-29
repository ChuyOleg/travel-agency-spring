package com.oleh.chui.model.entity;

import com.oleh.chui.model.entity.constant.ColumnName;
import com.oleh.chui.model.entity.constant.SequenceName;
import com.oleh.chui.model.entity.constant.TableName;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = TableName.ROLE_TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class Role {

    @Id
    @Column(name = ColumnName.ROLE_ID)
    @SequenceGenerator(
            name = SequenceName.ROLE_SEQUENCE_NAME,
            sequenceName = SequenceName.ROLE_SEQUENCE_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = SequenceName.ROLE_SEQUENCE_NAME
    )
    private Long id;

    @Column(name = ColumnName.ROLE)
    @Enumerated(EnumType.STRING)
    private RoleEnum value;

    public Role(RoleEnum roleEnum) {
        this.value = roleEnum;
    }

    public enum RoleEnum {
        UNKNOWN,
        USER,
        MANAGER,
        ADMIN
    }

}
