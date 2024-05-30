package com.tea.paradise.repository;

import com.tea.paradise.enums.VariantType;
import com.tea.paradise.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> {
    Optional<Variant> findByTitle(VariantType title);
}
