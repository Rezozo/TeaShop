package com.tea.paradise.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tea.paradise.model.id.PackageBucketId;
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
@Table(name = "package_bucket")
@IdClass(PackageBucketId.class)
public class PackageBucket {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bucket_id")
    @JsonBackReference
    private Bucket bucket;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    @JsonBackReference
    private Package pack;

    @Column
    private int quantity;
}
