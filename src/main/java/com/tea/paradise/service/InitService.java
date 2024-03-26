package com.tea.paradise.service;

import com.tea.paradise.enums.OrderTrackingStatus;
import com.tea.paradise.enums.UserRole;
import com.tea.paradise.enums.VariantType;
import com.tea.paradise.model.OrderStatus;
import com.tea.paradise.model.Role;
import com.tea.paradise.model.Variant;
import com.tea.paradise.repository.OrderStatusRepository;
import com.tea.paradise.repository.RoleRepository;
import com.tea.paradise.repository.VariantRepository;
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
    VariantRepository variantRepository;

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

    public void initVariants() {
        if (variantRepository.count() == 0) {
            for (VariantType variantType : VariantType.values()) {
                Variant variantEntity = new Variant()
                        .setTitle(variantType);
                variantRepository.save(variantEntity);
            }
        }
    }
}
