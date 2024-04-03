package com.tea.paradise.controller;

import com.tea.paradise.dto.pagination.PageResult;
import com.tea.paradise.dto.pagination.PagingCommand;
import com.tea.paradise.dto.pagination.PagingResponse;
import com.tea.paradise.dto.pagination.filters.ReviewFilter;
import com.tea.paradise.dto.saveDto.ReviewSaveDto;
import com.tea.paradise.service.sorting.impl.ReviewSorting;
import com.tea.paradise.service.specification.impl.ReviewSpecification;
import com.tea.paradise.dto.review.ReviewDto;
import com.tea.paradise.dto.review.mapper.ReviewMapper;
import com.tea.paradise.enums.ReviewSortType;
import com.tea.paradise.model.Review;
import com.tea.paradise.repository.ReviewRepository;
import com.tea.paradise.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "Операции с отзывами")
@RestController
@RequestMapping("/review")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ReviewController {
    ReviewService reviewService;
    ReviewSorting reviewSorting;
    ReviewSpecification reviewSpecification;
    ReviewRepository reviewRepository;
    ReviewMapper reviewMapper;

    @Operation(summary = "Проверка наличия отзыва для продукта у пользователя")
    @GetMapping
    public ReviewDto findReviewByProductAndUser(@RequestParam Long productId) {
        Review review = reviewService.getReviewByUserAndProduct(productId);
        return Objects.isNull(review) ?
                new ReviewDto() :
                reviewMapper.mapToDto(review);
    }

    @Operation(summary = "Поиск отзывов по фильтрам/сортировке")
    @PostMapping("actions/search-by-filter")
    public PagingResponse<ReviewDto> findReviews(
            @RequestBody PagingCommand<ReviewFilter, ReviewSortType> pagingCommand
    ) {
        Page<Review> reviews = reviewRepository.findAllWithFilters(pagingCommand, reviewSpecification, reviewSorting, null);
        return new PagingResponse<ReviewDto>()
                .setData(reviews.stream()
                        .map(reviewMapper::mapToDto)
                        .toList())
                .setPagingCommand(new PageResult()
                        .setPages(reviews.getTotalPages())
                        .setTotal((int) reviews.getTotalElements()));
    }

    @Operation(summary = "Добавление отзыва")
    @PostMapping
    public ReviewDto addReview(@Valid @RequestBody ReviewSaveDto reviewSaveDto) {
        Review review = reviewService.saveReview(reviewMapper.mapSaveModel(reviewSaveDto));
        return reviewMapper.mapToDto(review);
    }

    @Operation(summary = "Обновление отзыва")
    @PutMapping
    public ReviewDto updateReview(@Valid @RequestBody ReviewSaveDto reviewSaveDto) {
        Review review = reviewService.updateReview(reviewMapper.mapSaveModel(reviewSaveDto));
        return reviewMapper.mapToDto(review);
    }

    @Operation(summary = "Удаление отзыва")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.ok("Отзыв успешно удалён");
    }
}
