package com.tea.paradise.model;

import com.tea.paradise.enums.OrderTrackingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_status_id_seq")
    @SequenceGenerator(name = "order_status_id_seq", sequenceName = "order_status_id_seq", allocationSize = 1)
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderTrackingStatus status;
}
