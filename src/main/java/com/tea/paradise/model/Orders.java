package com.tea.paradise.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JoinColumn(name = "recipient_id")
    @JsonBackReference
    private Recipient recipient;

    @ManyToOne
    @JoinColumn(name ="address_id")
    @JsonBackReference
    private Address address;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<PackageOrder> packageOrders;

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
