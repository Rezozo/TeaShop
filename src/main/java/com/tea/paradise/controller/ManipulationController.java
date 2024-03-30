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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Операции для разработки")
@RestController
@RequestMapping("/manipulation")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ManipulationController {
    CategoryService categoryService;
    CategoryMapper categoryMapper;

    @Operation(summary = "Создание категорий")
    @PostMapping("/category")
    public CategoryDto uploadImages(@RequestParam("file") MultipartFile file,
                                    @RequestParam(name = "title") String title,
                                    @RequestParam(name = "parent") ParentCategory parentCategory) {
        return categoryMapper.toDto(categoryService.create(file, title, parentCategory));
    }


}
