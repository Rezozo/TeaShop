package com.tea.paradise.dto;

import com.tea.paradise.model.Image;
import com.tea.paradise.model.Product;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;
    private List<Product> products;
    private Image image;
    private String name;
}
