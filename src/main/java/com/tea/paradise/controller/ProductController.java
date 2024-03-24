package com.tea.paradise.controller;

import com.tea.paradise.dto.ProductFullDto;
import com.tea.paradise.dto.ProductShortDto;
import com.tea.paradise.dto.requests.SearchFilterProducts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "Product operation")
@RestController
@RequestMapping("/products")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductController {

    @Operation(summary = "Получение продукта по идентификатору")
    @GetMapping("{id}")
    public ProductFullDto getProductById(@PathVariable Long id) {
        return null; // TODO
    }

    @Operation(summary = "Поиск продуктов по фильтрам/сортировке/названию")
    @PostMapping("actions/search-by-filter")
    public Set<ProductShortDto> findShortProduct(@Valid @RequestBody SearchFilterProducts filter) {
        return null; // TODO
    }
}
