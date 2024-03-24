package com.tea.paradise.dto;

import com.tea.paradise.enums.UserRole;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private UserRole role;

    private List<Long> favorites;

    private BucketDto bucket;

    private List<AddressDto> addresses;

    private String name;

    private String surname;

    private String email;

    private Integer teaBonuses;

    private ZonedDateTime createdDate;
}
