package com.tea.paradise.controller;

import com.tea.paradise.dto.ProductFullDto;
import com.tea.paradise.dto.mapper.ImageMapper;
import com.tea.paradise.dto.mapper.ProductMapper;
import com.tea.paradise.dto.saveDto.ProductSaveDto;
import com.tea.paradise.model.Product;
import com.tea.paradise.service.ImageService;
import com.tea.paradise.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Accounting operations")
@RestController
@RequestMapping("/accounting")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AccountingController {
    ProductService productService;
    ProductMapper productMapper;
    ImageService imageService;
    ImageMapper imageMapper;

    @Operation(summary = "Создание нового продукта")
    @PostMapping("/product")
    public ProductFullDto createProduct(@Valid @RequestBody ProductSaveDto product) {
        Product productEntity = productService.create(productMapper.mapSaveModel(product));
        // TODO вызываем пекадж сервис тут и потом сеттим пекаджы к product
        return productMapper.mapFullDto(productEntity);
    }

    @Operation(summary = "Добавление любых картинок")
    @PostMapping("/upload-images")
    public List<Long> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        return imageService.saveImages(files);
    }
}
