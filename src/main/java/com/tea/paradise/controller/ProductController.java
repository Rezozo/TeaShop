package com.tea.paradise.controller;

import com.tea.paradise.dto.product.ProductFullDto;
import com.tea.paradise.dto.product.ProductShortDto;
import com.tea.paradise.dto.product.mapper.ProductMapper;
import com.tea.paradise.dto.pagination.PageResult;
import com.tea.paradise.dto.pagination.PagingCommand;
import com.tea.paradise.dto.pagination.PagingResponse;
import com.tea.paradise.dto.pagination.filters.ProductFilter;
import com.tea.paradise.service.sorting.impl.ProductSorting;
import com.tea.paradise.service.specification.impl.ProductSpecification;
import com.tea.paradise.enums.ProductSortType;
import com.tea.paradise.model.Product;
import com.tea.paradise.model.Users;
import com.tea.paradise.repository.ProductRepository;
import com.tea.paradise.service.FavoriteService;
import com.tea.paradise.service.ProductService;
import com.tea.paradise.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Операции с продуктами")
@RestController
@RequestMapping("/products")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductController {
    ProductMapper productMapper;
    ProductService productService;
    ProductRepository productRepository;
    ProductSorting productSorting;
    ProductSpecification productSpecification;
    UserService userService;
    FavoriteService favoriteService;

    @Operation(summary = "Получение продукта по идентификатору")
    @GetMapping("{id}")
    public ProductFullDto getProductById(@PathVariable Long id) {
        return productMapper.mapFullDto(productService.getById(id));
    }

    @Operation(summary = "Поиск продуктов по фильтрам/сортировке/названию")
    @PostMapping("actions/search-by-filter")
    public PagingResponse<ProductShortDto> findShortProduct(
            @RequestBody PagingCommand<ProductFilter, ProductSortType> pagingCommand
    ) {
        Page<Product> products = productRepository.findAllWithFilters(pagingCommand, productSpecification, productSorting, null);
        return new PagingResponse<ProductShortDto>()
                .setData(products.stream()
                        .map(product -> {
                            Long userId = Optional.ofNullable(userService.getAuthInfo())
                                    .map(Users::getId)
                                    .orElse(0L);
                            return productMapper.mapShortDto(product, userId);
                        })
                        .toList())
                .setPagingCommand(new PageResult()
                        .setPages(products.getTotalPages())
                        .setTotal((int) products.getTotalElements()));
    }

    @Operation(summary = "Добавить или удалить продукт из избранного")
    @PutMapping("actions/favorite/{id}")
    public ResponseEntity<String> updateFavorite(@PathVariable Long id) {
        return ResponseEntity.ok(favoriteService.updateFavorite(userService.getAuthInfo().getId(), id));
    }
}
