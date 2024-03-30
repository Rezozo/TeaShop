package com.tea.paradise.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tea.paradise.model.id.PackageOrderId;
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
@Table(name = "package_order")
@IdClass(PackageOrderId.class)
public class PackageOrder {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    @JsonBackReference
    private Package pack;

    @Column
    private int quantity;
}
