package com.tea.paradise.controller;

import com.tea.paradise.dto.order.OrderDto;
import com.tea.paradise.dto.order.OrderShortDto;
import com.tea.paradise.dto.order.mapper.OrderMapper;
import com.tea.paradise.dto.pagination.PageResult;
import com.tea.paradise.dto.pagination.PagingCommand;
import com.tea.paradise.dto.pagination.PagingResponse;
import com.tea.paradise.dto.pagination.filters.OrderFilter;
import com.tea.paradise.dto.saveDto.ClientOrderSaveDto;
import com.tea.paradise.dto.packages.ShortOrderPackageDto;
import com.tea.paradise.enums.OrderSortType;
import com.tea.paradise.model.Orders;
import com.tea.paradise.model.Users;
import com.tea.paradise.repository.OrderRepository;
import com.tea.paradise.service.OrderService;
import com.tea.paradise.service.PackageService;
import com.tea.paradise.service.UserService;
import com.tea.paradise.service.sorting.impl.OrderSorting;
import com.tea.paradise.service.specification.impl.OrderSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Операции с заказами")
@RestController
@RequestMapping("/orders")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OrderController {
    PackageService packageService;
    UserService userService;
    OrderService orderService;
    OrderMapper orderMapper;
    OrderRepository orderRepository;
    OrderSpecification orderSpecification;
    OrderSorting orderSorting;

    @Operation(summary = "Поиск всех заказов по фильтрам/сортировке")
    @PostMapping("actions/search-by-filter")
    public PagingResponse<OrderShortDto> getOrders(
            @RequestBody PagingCommand<OrderFilter, OrderSortType> pagingCommand
    ) {
        Page<Orders> orders = orderRepository.findAllWithFilters(pagingCommand, orderSpecification, orderSorting, null);
        return new PagingResponse<OrderShortDto>()
                .setData(orders.stream()
                        .map(orderMapper::mapToShortDto).toList())
                .setPagingCommand(new PageResult()
                        .setPages(orders.getTotalPages())
                        .setTotal((int) orders.getTotalElements()));
    }

    @Operation(summary = "Получение полной информации о заказе")
    @GetMapping("{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderMapper.mapToDto(orderService.getById(id));
    }

    @Operation(summary = "Валидация заказа (проверка наличия на складе) перед оплатой")
    @PostMapping("/validate")
    public ResponseEntity<String> isValidOrder(@RequestBody List<ShortOrderPackageDto> shortOrderPackageDtos) {
        List<String> invalidProducts = packageService.isValid(shortOrderPackageDtos);

        if (invalidProducts.isEmpty()) {
            return ResponseEntity.ok("Все товары в наличии");
        }
        String message = "Недостаточное количество товаров: " + String.join(", ", invalidProducts);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @Operation(summary = "Сохранить оплаченный заказ")
    @PostMapping
    public OrderDto createNewOrder(@Valid @RequestBody ClientOrderSaveDto clientOrderSaveDto) {
        Users users = userService.getLoggedUserInfo();
        Orders order = orderMapper.mapToSaveModel(clientOrderSaveDto, users);
        return orderMapper.mapToDto(orderService.savePaid(order));
    }
}
