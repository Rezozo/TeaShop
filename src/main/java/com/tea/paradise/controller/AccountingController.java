package com.tea.paradise.controller;

import com.tea.paradise.dto.order.OrderDto;
import com.tea.paradise.dto.order.mapper.OrderMapper;
import com.tea.paradise.dto.product.ProductFullDto;
import com.tea.paradise.dto.packages.mapper.PackageMapper;
import com.tea.paradise.dto.product.mapper.ProductMapper;
import com.tea.paradise.dto.saveDto.ProductSaveDto;
import com.tea.paradise.enums.OrderTrackingStatus;
import com.tea.paradise.model.Image;
import com.tea.paradise.model.Package;
import com.tea.paradise.model.Product;
import com.tea.paradise.service.ImageService;
import com.tea.paradise.service.OrderService;
import com.tea.paradise.service.PackageService;
import com.tea.paradise.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Операции раздела учета продаж")
@RestController
@RequestMapping("/accounting")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AccountingController { // TODO order diagrams & update products
    ProductService productService;
    ProductMapper productMapper;
    ImageService imageService;
    PackageService packageService;
    PackageMapper packageMapper;
    OrderService orderService;
    OrderMapper orderMapper;

    @Operation(summary = "Создание нового продукта")
    @PostMapping("product")
    public ProductFullDto createProduct(@Valid @RequestBody ProductSaveDto product) {
        Product productEntity = productService.create(productMapper.mapSaveModel(product));
        List<Package> packages = packageService.saveAll(product.getPackages().stream()
                .map(packageSaveDto -> packageMapper.mapSaveModel(packageSaveDto, productEntity))
                .toList());
        productEntity.setPackages(packages);

        return productMapper.mapFullDto(productEntity);
    }

    @Operation(summary = "Изменение статуса или трек номера заказа")
    @PutMapping("orders/{orderId}")
    public OrderDto updateOrderStatus(@PathVariable Long orderId,
                                      @RequestParam(required = false) OrderTrackingStatus status,
                                      @RequestParam(required = false) String track) {
        return  orderMapper.mapToDto(orderService.updateStatusOrTrack(orderId, status, track));
    }

    @Operation(summary = "Добавление любых картинок для продуктов/категорий/отзывов")
    @PostMapping("upload-images")
    public List<Long> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        return imageService.saveImages(files).stream()
                .map(Image::getId)
                .toList();
    }

    @Operation(summary = "Удаление продукта")
    @DeleteMapping("product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.ok("Продукт успешно удалён");
    }
}
