package com.tea.paradise.dto.saveDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSaveDto {
    private Long id;
    @NotEmpty
    private List<Long> images;
    @NotEmpty
    private List<PackageSaveDto> packages;
    @NotNull
    private Integer categoryId;
    @NotNull
    private String article;
    @NotNull
    private String title;
    @NotNull
    private String description;
    private Short discount;
    @NotNull
    private Boolean active;
}
