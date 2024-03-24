package com.tea.paradise.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private Long id;
    private String city;
    private String address;
    private Short flat;
    private Short floor;
    private Short entrance;
    private String intercomCode;
}
