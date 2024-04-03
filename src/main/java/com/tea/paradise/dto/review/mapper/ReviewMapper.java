package com.tea.paradise.dto.review.mapper;

import com.tea.paradise.dto.review.ReviewDto;
import com.tea.paradise.dto.saveDto.ReviewSaveDto;
import com.tea.paradise.model.Image;
import com.tea.paradise.model.Product;
import com.tea.paradise.model.Review;
import com.tea.paradise.model.Users;
import com.tea.paradise.repository.ImagesRepository;
import com.tea.paradise.repository.ProductRepository;
import com.tea.paradise.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;

@Mapper(imports = {ZonedDateTime.class})
public abstract class ReviewMapper {
    @Autowired
    ImagesRepository imagesRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserService userService;

    @Mapping(source = "review.id", target = "id")
    @Mapping(source = "review.user.name", target = "userName")
    @Mapping(source = "review.product.id", target = "productId")
    @Mapping(source = "review.product.title", target = "productTitle")
    @Mapping(source = "image", target = "productImage")
    public abstract ReviewDto toDto(Review review, Image image);

    @Mapping(source = "saveDto.id", target = "id")
    @Mapping(source = "images", target = "images")
    @Mapping(source = "users", target = "user")
    @Mapping(target = "createdDate", expression = "java(ZonedDateTime.now())")
    public abstract Review toSaveModel(ReviewSaveDto saveDto,
                                       List<Image> images,
                                       Product product,
                                       Users users);

    public ReviewDto mapToDto(Review review) {
        Image productImage = review.getProduct().getImages().stream()
                .findFirst().orElseThrow();
        return toDto(review, productImage);
    }

    public Review mapSaveModel(ReviewSaveDto saveDto) {
        Users users = userService.getLoggedUserInfo();
        List<Image> images = imagesRepository.findAllByIdIn(saveDto.getImages());
        Product product = productRepository.findById(saveDto.getProductId()).orElseThrow();
        return toSaveModel(saveDto, images, product, users);
    }
}
