package com.tea.paradise.dto.saveDto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewSaveDto {
    private Long id;
    @NotNull
    private Long productId;
    @NotEmpty
    private List<Long> images;
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Short stars;
    @NotNull
    private String reviewText;
}
