package com.tea.paradise.repository;

import com.tea.paradise.enums.UserRole;
import com.tea.paradise.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByTitle(UserRole title);
}
