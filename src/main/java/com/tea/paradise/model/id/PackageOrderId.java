package com.tea.paradise.model.id;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageOrderId implements Serializable {
    private Long order;
    private Long pack;
}
