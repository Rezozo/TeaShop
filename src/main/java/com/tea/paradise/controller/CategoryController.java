package com.tea.paradise.controller;

import com.tea.paradise.dto.category.CategoryDto;
import com.tea.paradise.dto.category.mapper.CategoryMapper;
import com.tea.paradise.enums.ParentCategory;
import com.tea.paradise.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Операции с категориями")
@RestController
@RequestMapping("/category")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CategoryController {
    CategoryService categoryService;
    CategoryMapper categoryMapper;

    @Operation(summary = "Получение категорий по родительской")
    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam ParentCategory parent) {
        return categoryService.getAllByParent(parent).stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}
