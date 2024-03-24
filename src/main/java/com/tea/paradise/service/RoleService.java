package com.tea.paradise.service;

import com.tea.paradise.enums.UserRole;
import com.tea.paradise.model.Role;
import com.tea.paradise.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RoleService {
    RoleRepository roleRepository;

    public Role getByTitle(UserRole userRole) {
        return roleRepository.findByTitle(userRole).orElseThrow();
    }

}
