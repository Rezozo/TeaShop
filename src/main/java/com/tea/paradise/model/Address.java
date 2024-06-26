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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_seq")
    @SequenceGenerator(name = "address_id_seq", sequenceName = "address_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users user;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Orders> orders;

    @Column
    private String city;

    @Column
    private String address;

    @Column
    private Short flat;

    @Column
    private Short floor;

    @Column
    private Short entrance;

    @Column(name = "intercom_code")
    private String intercomCode;

    @Column(name = "active", nullable = false)
    private boolean active;
}
