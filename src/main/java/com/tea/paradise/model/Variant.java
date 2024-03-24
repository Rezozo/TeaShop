package com.tea.paradise.model;

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
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variant_id_seq")
    @SequenceGenerator(name = "variant_id_seq", sequenceName = "variant_id_seq", allocationSize = 1)
    private Integer id;

    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Package> packages;

    @Column
    private String title;
}
