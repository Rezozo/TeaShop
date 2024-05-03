package com.tea.paradise.service;

import com.tea.paradise.enums.OrderTrackingStatus;
import com.tea.paradise.model.OrderStatus;
import com.tea.paradise.model.Orders;
import com.tea.paradise.model.Package;
import com.tea.paradise.model.Users;
import com.tea.paradise.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.tea.paradise.config.CacheConfig.ORDERS_INFO;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderService {
    BucketService bucketService;
    OrderStatusRepository statusRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;
    PackageRepository packageRepository;
    PackageOrderRepository packageOrderRepository;

    public OrderStatus getStatusByTitle(OrderTrackingStatus status) {
        return statusRepository.findByStatus(status).orElseThrow();
    }

    @CachePut(value = ORDERS_INFO, key = "'order:' + #id")
    public Orders updateStatusOrTrack(Long id, OrderTrackingStatus status, String track) {
        Orders order = getById(id);
        if (Objects.nonNull(status)) {
            order.setOrderStatus(statusRepository.findByStatus(status).orElseThrow());
        }
        if (Objects.nonNull(track)) {
            order.setTrackNumber(track);
        }
        return orderRepository.save(order);
    }

    @Cacheable(value = ORDERS_INFO, key = "'order:' + #id")
    public Orders getById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Orders savePaid(Orders orders) {
        Orders saveOrder = orderRepository.save(orders);
        saveOrder.getPackageOrders().forEach(packageOrder -> packageOrder.setOrder(saveOrder));
        packageOrderRepository.saveAll(saveOrder.getPackageOrders());

        Users user = orders.getAddress().getUser();
        user.setTeaBonuses(user.getTeaBonuses() + saveOrder.getBonusesAccrued() - saveOrder.getBonusesSpent());
        userRepository.save(user);
        bucketService.clear(user.getId());

        saveOrder.getPackageOrders().forEach(packageOrder -> {
            Package pack = packageOrder.getPack();
            if (pack.getQuantity() < packageOrder.getQuantity()) {
                throw new ConstraintViolationException("Недостаточно товара на складе", null);
            }
            pack.setQuantity(pack.getQuantity() - packageOrder.getQuantity());
            packageRepository.save(pack);
        });

        return saveOrder;
    }
}
