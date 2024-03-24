package com.tea.paradise.service;

import com.tea.paradise.enums.OrderTrackingStatus;
import com.tea.paradise.enums.UserRole;
import com.tea.paradise.model.OrderStatus;
import com.tea.paradise.model.Role;
import com.tea.paradise.repository.OrderStatusRepository;
import com.tea.paradise.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class InitService {
    OrderStatusRepository orderStatusRepository;
    RoleRepository roleRepository;

    public void initRoles() {
        if (roleRepository.count() == 0) {
            for (UserRole userRole : UserRole.values()) {
                Role role = new Role()
                        .setTitle(userRole);
                roleRepository.save(role);
            }
        }
    }

    public void initOrderStatuses() {
        if (orderStatusRepository.count() == 0) {
            for (OrderTrackingStatus orderStatus : OrderTrackingStatus.values()) {
                OrderStatus orderStatusEntity = new OrderStatus()
                        .setStatus(orderStatus);
                orderStatusRepository.save(orderStatusEntity);
            }
        }
    }
}
