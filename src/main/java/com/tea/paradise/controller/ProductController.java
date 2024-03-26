package com.tea.paradise.controller;

import com.tea.paradise.dto.ProductFullDto;
import com.tea.paradise.dto.ProductShortDto;
import com.tea.paradise.dto.mapper.ProductMapper;
import com.tea.paradise.dto.pagination.PageResult;
import com.tea.paradise.dto.pagination.PagingCommand;
import com.tea.paradise.dto.pagination.PagingResponse;
import com.tea.paradise.dto.pagination.filters.ProductFilter;
import com.tea.paradise.dto.sorting.impl.ProductSorting;
import com.tea.paradise.dto.specification.impl.ProductSpecification;
import com.tea.paradise.enums.ProductSortType;
import com.tea.paradise.model.Product;
import com.tea.paradise.repository.ProductRepository;
import com.tea.paradise.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product operation")
@RestController
@RequestMapping("/products")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductController {
    ProductMapper productMapper;
    ProductRepository productRepository;
    ProductSorting productSorting;
    ProductSpecification productSpecification;
    UserService userService;

    @Operation(summary = "Получение продукта по идентификатору")
    @GetMapping("{id}")
    public ProductFullDto getProductById(@PathVariable Long id) {
        return null; // TODO
    }

    @Operation(summary = "Поиск продуктов по фильтрам/сортировке/названию")
    @PostMapping("actions/search-by-filter")
    public PagingResponse<ProductShortDto> findShortProduct(
            @RequestBody PagingCommand<ProductFilter, ProductSortType> pagingCommand
    ) {
        Page<Product> products = productRepository.findAllWithFilters(pagingCommand, productSpecification, productSorting, null);
        return new PagingResponse<ProductShortDto>()
                .setData(products.stream()
                        .map(product -> productMapper.mapShortDto(product, userService.getAuthInfo().getId()))
                        .toList())
                .setPagingCommand(new PageResult()
                        .setPages(products.getTotalPages())
                        .setTotal((int) products.getTotalElements()));
    }
}
