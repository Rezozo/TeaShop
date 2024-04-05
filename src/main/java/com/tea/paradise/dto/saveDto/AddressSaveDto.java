package com.tea.paradise.dto.saveDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressSaveDto {
    @NotNull
    @NotEmpty
    private String city;
    @NotNull
    @NotEmpty
    private String address;
    private Short flat;
    private Short floor;
    private Short entrance;
    private String intercomCode;
}
