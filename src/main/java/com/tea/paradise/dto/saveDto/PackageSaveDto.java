package com.tea.paradise.dto.saveDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageSaveDto {
    @NotNull
    private Integer variantId;
    @NotNull
    private Integer quantity;
    @NotNull
    private Double price;
}
