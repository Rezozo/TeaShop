package com.tea.paradise.controller;

import com.tea.paradise.dto.order.ClientOrderSaveDto;
import com.tea.paradise.dto.packages.ShortPackageDto;
import com.tea.paradise.service.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Операции с заказами")
@RestController
@RequestMapping("/orders")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OrderController {
    PackageService packageService;

    @Operation(summary = "Валидация заказа (проверка наличия на складе) перед оплатой")
    @PostMapping("/validate")
    public ResponseEntity<String> isValidOrder(@RequestBody List<ShortPackageDto> shortPackageDtos) {
        List<String> invalidProducts = packageService.isValid(shortPackageDtos);

        if (invalidProducts.isEmpty()) {
            return ResponseEntity.ok("Все товары в наличии");
        }
        String message = "Недостаточное количество товаров: " + String.join(", ", invalidProducts);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @Operation(summary = "Сохранить оплаченный заказ")
    @PostMapping
    public void createNewOrder(@Valid @RequestBody ClientOrderSaveDto clientOrderSaveDto) {

    }
}
