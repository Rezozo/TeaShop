package com.tea.paradise.dto.review;

import com.tea.paradise.dto.image.ImageDto;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private ZonedDateTime createdDate;
    private String userName;
    private Short stars;
    private String reviewText;
    private List<ImageDto> images;
    private Long productId;
    private String productTitle;
    private ImageDto productImage;
}
