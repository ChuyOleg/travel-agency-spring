package com.oleh.chui.model.entity;

import com.oleh.chui.model.entity.constant.ColumnName;
import com.oleh.chui.model.entity.constant.SequenceName;
import com.oleh.chui.model.entity.constant.TableName;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = TableName.ORDER_TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"user", "tour"})
public class Order {

    @Id
    @Column(name = ColumnName.ORDER_ID)
    @SequenceGenerator(
            name = SequenceName.ORDER_SEQUENCE_NAME,
            sequenceName = SequenceName.ORDER_SEQUENCE_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = SequenceName.ORDER_SEQUENCE_NAME
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = ColumnName.USER_ID)
    private User user;

    @ManyToOne
    @JoinColumn(name = ColumnName.TOUR_ID)
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = ColumnName.STATUS_ID)
    private Status status;

    @Column(name = ColumnName.CREATION_DATE)
    private LocalDate creationDate;

    @Column(name = ColumnName.FINAL_PRICE)
    private BigDecimal finalPrice;

}
