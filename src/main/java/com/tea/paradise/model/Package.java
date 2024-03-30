package com.tea.paradise.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "pack")
    @JsonManagedReference
    private List<PackageBucket> packageBuckets;

    @OneToMany(mappedBy = "pack")
    @JsonManagedReference
    private List<PackageOrder> packageOrders;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    @JsonBackReference
    private Variant variant;

    @Column
    private Integer quantity;

    @Column
    private Double price;
}
