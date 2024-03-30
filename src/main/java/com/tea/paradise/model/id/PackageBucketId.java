package com.tea.paradise.model.id;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageBucketId implements Serializable {
    private Long bucket;
    private Long pack;
}
