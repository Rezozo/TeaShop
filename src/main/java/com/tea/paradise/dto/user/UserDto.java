package com.tea.paradise.dto.user;

import com.tea.paradise.dto.address.AddressDto;
import com.tea.paradise.dto.bucket.BucketDto;
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

    private Integer ordersCount;

    private ZonedDateTime createdDate;
}
