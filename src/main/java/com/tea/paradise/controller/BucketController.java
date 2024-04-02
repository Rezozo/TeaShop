package com.tea.paradise.controller;

import com.tea.paradise.dto.bucket.BucketDto;
import com.tea.paradise.dto.bucket.mapper.BucketMapper;
import com.tea.paradise.dto.saveDto.ProductToBucketDto;
import com.tea.paradise.service.BucketService;
import com.tea.paradise.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Операции с корзиной")
@RestController
@RequestMapping("/bucket")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BucketController {
    BucketService bucketService;
    BucketMapper bucketMapper;
    UserService userService;

    @Operation(summary = "Получение корзины (только для авторизированных пользователей)")
    @GetMapping
    public BucketDto getUserBucket() {
        return bucketMapper.map(bucketService.getByUserId(userService.getAuthInfo().getId()));
    }

    @Operation(summary = "Добавление товара в корзину")
    @PostMapping
    public BucketDto addProductToBucket(@RequestBody ProductToBucketDto product) {
        return bucketMapper.map(bucketService.addProduct(userService.getAuthInfo().getId(), product));
    }

    @Operation(summary = "Удаление товара из корзины")
    @DeleteMapping("{packId}")
    public BucketDto deleteProductFromBucket(@PathVariable Long packId) {
        return bucketMapper.map(bucketService.deletePackById(userService.getAuthInfo().getId(), packId));
    }

    @Operation(summary = "Полная очистка корзины", description = "После успешной оплаты")
    @DeleteMapping("clear")
    public BucketDto clearBucket() {
        return bucketMapper.map(bucketService.clear(userService.getAuthInfo().getId()));
    }
}
