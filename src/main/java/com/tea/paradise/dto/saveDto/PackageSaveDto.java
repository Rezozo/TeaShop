package com.tea.paradise.dto.saveDto;

import com.tea.paradise.enums.VariantType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageSaveDto {
    @NotNull
    private VariantType variant;
    @NotNull
    private Integer quantity;
    @NotNull
    private Double price;
}
