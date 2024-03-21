package com.tea.paradise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "package_id_seq")
    @SequenceGenerator(name = "package_id_seq", sequenceName = "package_id_seq", allocationSize = 1)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "package_bucket",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "bucket_id")
    )
    private List<Bucket> buckets;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "package_order",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Orders> orders;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;

    @Column
    private Integer quantity;

    @Column
    private Double price;
}
