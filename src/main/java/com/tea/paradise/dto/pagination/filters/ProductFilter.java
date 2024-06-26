package com.tea.paradise.dto.pagination.filters;

import com.tea.paradise.enums.VariantType;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFilter {
    private boolean onlyPopular;
    private boolean onlyNew;
    private boolean onlyFavorite;
    private Integer categoryId;
    private String searchString;
    private Boolean inStock;
    private Boolean isActive;
    private Double minPrice;
    private Double maxPrice;
    private List<VariantType> variantTypes;
}
