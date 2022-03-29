package com.oleh.chui.model.entity;

import com.oleh.chui.model.entity.constant.ColumnName;
import com.oleh.chui.model.entity.constant.SequenceName;
import com.oleh.chui.model.entity.constant.TableName;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = TableName.STATUS_TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class Status {

    @Id
    @Column(name = ColumnName.STATUS_ID)
    @SequenceGenerator(
            name = SequenceName.STATUS_SEQUENCE_NAME,
            sequenceName = SequenceName.STATUS_SEQUENCE_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = SequenceName.STATUS_SEQUENCE_NAME
    )
    private Long id;

    @Column(name = ColumnName.STATUS)
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
