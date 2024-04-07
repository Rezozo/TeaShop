package com.tea.paradise.repository;

import com.tea.paradise.model.PackageBucket;
import com.tea.paradise.model.id.PackageBucketId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageBucketRepository extends JpaRepository<PackageBucket, PackageBucketId> {
    void deleteAllByPack_Product_Id(Long productId);
}
