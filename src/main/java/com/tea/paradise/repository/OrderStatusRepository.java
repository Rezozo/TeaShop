package com.tea.paradise.repository;

import com.tea.paradise.enums.OrderTrackingStatus;
import com.tea.paradise.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    Optional<OrderStatus> findByStatus(OrderTrackingStatus status);
}
