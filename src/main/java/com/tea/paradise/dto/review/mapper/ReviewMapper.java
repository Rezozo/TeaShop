package com.tea.paradise.dto.review.mapper;

import com.tea.paradise.dto.review.ReviewDto;
import com.tea.paradise.model.Image;
import com.tea.paradise.model.Review;
import com.tea.paradise.repository.ImagesRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class ReviewMapper {
    @Autowired
    ImagesRepository imagesRepository;

    @Mapping(source = "review.id", target = "id")
    @Mapping(source = "review.user.name", target = "userName")
    @Mapping(source = "review.product.id", target = "productId")
    @Mapping(source = "review.product.title", target = "productTitle")
    @Mapping(source = "image", target = "productImage")
    public abstract ReviewDto toDto(Review review, Image image);

    public ReviewDto mapToDto(Review review) {
        Image productImage = review.getProduct().getImages().stream()
                .findFirst().orElseThrow();
        return toDto(review, productImage);
    }

}
