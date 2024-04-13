package com.tea.paradise.dto.order.mapper;

import com.tea.paradise.dto.order.OrderDto;
import com.tea.paradise.dto.order.OrderShortDto;
import com.tea.paradise.dto.packages.PackageOrderDto;
import com.tea.paradise.dto.packages.mapper.PackageMapper;
import com.tea.paradise.dto.saveDto.ClientOrderSaveDto;
import com.tea.paradise.enums.OrderTrackingStatus;
import com.tea.paradise.model.*;
import com.tea.paradise.model.Package;
import com.tea.paradise.service.AddressService;
import com.tea.paradise.service.OrderService;
import com.tea.paradise.service.PackageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Mapper(imports = {ZonedDateTime.class})
public abstract class OrderMapper {
    @Autowired
    PackageService packageService;
    @Autowired
    AddressService addressService;
    @Autowired
    OrderService orderService;
    @Autowired
    PackageMapper packageMapper;

    @Value("${tea.bonuses-percent}")
    private double bonusPercent;

    @Mapping(source = "address", target = "address")
    @Mapping(source = "orderSaveDto.recipientDto", target = "recipient")
    @Mapping(source = "status", target = "orderStatus")
    @Mapping(target = "createdDate", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "modifiedDate", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "recipient.orders", ignore = true)
    @Mapping(target = "trackNumber", ignore = true)
    @Mapping(target = "id", ignore = true )
    public abstract Orders toSaveModel(ClientOrderSaveDto orderSaveDto,
                                       Address address,
                                       List<PackageOrder> packageOrders,
                                       Integer bonusesAccrued,
                                       Integer bonusesSpent,
                                       OrderStatus status);

    public Orders mapToSaveModel(ClientOrderSaveDto orderSaveDto, Users users) {
        AtomicReference<Double> totalCost = new AtomicReference<>(0.0);

        List<PackageOrder> packageOrders = orderSaveDto.getShortPackageDtos().stream()
                .map(shortPackageDto -> {
                    Package pack = packageService.getById(shortPackageDto.getPackageId());
                    Double price = pack.getPrice();
                    totalCost.updateAndGet(v -> {
                        if (Objects.nonNull(pack.getProduct().getDiscount())) {
                            return v + ((price - (price * ((double) pack.getProduct().getDiscount() / 100))) * shortPackageDto.getCount());
                        }
                        return v + price * shortPackageDto.getCount();
                    });
                    return new PackageOrder()
                            .setPack(pack)
                            .setFixedPrice(Objects.nonNull(pack.getProduct().getDiscount()) ?
                                    price - (price * ((double) pack.getProduct().getDiscount() / 100)) : price)
                            .setQuantity(shortPackageDto.getCount());
                })
                .toList();

        Integer bonusesAccrued = (int) (totalCost.get() * bonusPercent);
        Integer bonusesSpent = 0;
        if (orderSaveDto.getIsPayWithBonuses()) {
            bonusesSpent = users.getTeaBonuses();
        }
        Address address = addressService.getById(orderSaveDto.getAddressId());
        OrderStatus status = orderService.getStatusByTitle(OrderTrackingStatus.NEW);

        return toSaveModel(orderSaveDto, address, packageOrders, bonusesAccrued, bonusesSpent, status);
    }

    @Mapping(source = "orders.orderStatus.status", target = "status")
    @Mapping(source = "packageOrders", target = "packageOrders")
    public abstract OrderDto toDto(Orders orders,
                                   Double totalCost,
                                   List<PackageOrderDto> packageOrders);

    public OrderDto mapToDto(Orders orders) {
        Double totalCost = orders.getPackageOrders().stream()
                .mapToDouble(value -> value.getFixedPrice() * value.getQuantity())
                .sum();
        List<PackageOrderDto> packageOrderDtos = orders.getPackageOrders().stream()
                .map(packageOrder -> packageMapper.mapToOrderDto(packageOrder))
                .toList();
        return toDto(orders, totalCost, packageOrderDtos);
    }

    @Mapping(source = "orders.orderStatus.status", target = "status")
    @Mapping(source = "packageOrders", target = "packageOrders")
    public abstract OrderShortDto toShortDto(Orders orders,
                                   Double totalCost,
                                   List<PackageOrderDto> packageOrders);

    public OrderShortDto mapToShortDto(Orders orders) {
        Double totalCost = orders.getPackageOrders().stream()
                .mapToDouble(value -> value.getFixedPrice() * value.getQuantity())
                .sum();
        List<PackageOrderDto> packageOrderDtos = orders.getPackageOrders().stream()
                .map(packageOrder -> packageMapper.mapToOrderDto(packageOrder))
                .toList();
        return toShortDto(orders, totalCost, packageOrderDtos);
    }
}
