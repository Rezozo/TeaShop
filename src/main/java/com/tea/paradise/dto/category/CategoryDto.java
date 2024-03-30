package com.tea.paradise.dto.category;

import com.tea.paradise.dto.image.ImageDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Integer id;
    private ImageDto image;
    private String name;
}
