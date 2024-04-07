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
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipient_id_seq")
    @SequenceGenerator(name = "recipient_id_seq", sequenceName = "recipient_id_seq", allocationSize = 1)
    private Long id;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Orders> orders;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
}
