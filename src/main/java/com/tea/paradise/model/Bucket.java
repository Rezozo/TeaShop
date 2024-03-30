package com.tea.paradise.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
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
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bucket_id_seq")
    @SequenceGenerator(name = "bucket_id_seq", sequenceName = "bucket_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    @JsonBackReference
    private Users user;

    @OneToMany(mappedBy = "bucket")
    @JsonManagedReference
    private List<PackageBucket> packageBuckets;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    private ZonedDateTime modifiedDate;
}
