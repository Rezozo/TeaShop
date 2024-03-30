package com.tea.paradise.dto.bucket;

import com.tea.paradise.dto.image.ImageDto;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductBucketDto {
    private Long id;
    private String title;
    private Short discount;
    private List<ImageDto> images;
}
