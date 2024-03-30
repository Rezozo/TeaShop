package com.tea.paradise.dto.saveDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductToBucketDto {
    @NotNull
    private Long productId;
    @NotNull
    private Long packId;
    @NotNull
    @Min(value = 1, message = "Должно быть больше или равно 1")
    private Integer count;
}
