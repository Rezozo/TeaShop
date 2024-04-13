package com.tea.paradise.repository;

import com.tea.paradise.model.PackageOrder;
import com.tea.paradise.model.id.PackageOrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageOrderRepository extends JpaRepository<PackageOrder, PackageOrderId> {
}
