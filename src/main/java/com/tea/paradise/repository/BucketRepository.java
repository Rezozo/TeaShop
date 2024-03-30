package com.tea.paradise.repository;

import com.tea.paradise.model.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Bucket findBucketByUser_Id(Long userId);

    @Query(value = "SELECT EXISTS (SELECT * from package_bucket WHERE package_id = :packId AND bucket_id = :bucketId)", nativeQuery = true)
    boolean existsByPackageBuckets(@Param("bucketId") Long bucketId, @Param("packId") Long packId);
}
