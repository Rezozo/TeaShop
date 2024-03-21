package com.tea.paradise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(name = "favorite",
            joinColumns = @JoinColumn( name="user_id"),
            inverseJoinColumns = @JoinColumn( name="product_id")
    )
    private List<Product> favorites;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Bucket bucket;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Orders> orders;

    @ManyToMany
    @JoinTable(name = "user_address",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String email;

    @Column(name = "tea_bonuses")
    private Integer teaBonuses;

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    private ZonedDateTime createdDate;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @JsonIgnore
    @Column
    private String password;
}
