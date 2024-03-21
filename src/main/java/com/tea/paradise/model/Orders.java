package com.tea.paradise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users client;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus orderStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "package_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "package_id")
    )
    private List<Package> packages; // TODO pick quantity

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    private ZonedDateTime createdDate;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    private ZonedDateTime modifiedDate;

    @Column(name = "tea_bonuses_spent")
    private Integer bonusesSpent;

    @Column(name = "tea_bonuses_accrued")
    private Integer bonusesAccrued;

    @Column(name = "track_number")
    private String trackNumber;
}
