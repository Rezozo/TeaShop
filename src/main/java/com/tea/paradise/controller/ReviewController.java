package com.tea.paradise.controller;

import com.tea.paradise.dto.pagination.PageResult;
import com.tea.paradise.dto.pagination.PagingCommand;
import com.tea.paradise.dto.pagination.PagingResponse;
import com.tea.paradise.dto.pagination.filters.ReviewFilter;
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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
