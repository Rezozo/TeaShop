package com.tea.paradise.service;

import com.tea.paradise.dto.saveDto.ProductToBucketDto;
import com.tea.paradise.model.Bucket;
import com.tea.paradise.model.Package;
import com.tea.paradise.model.PackageBucket;
import com.tea.paradise.model.Users;
import com.tea.paradise.repository.BucketRepository;
import com.tea.paradise.repository.PackageRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BucketService {
    BucketRepository bucketRepository;
    PackageRepository packageRepository;

    public void createByUser(Users users) {
        bucketRepository.save(new Bucket()
                .setUser(users)
                .setModifiedDate(ZonedDateTime.now()));
    }

    public Bucket getByUserId(Long id) {
        return bucketRepository.findBucketByUser_Id(id);
    }

    public Bucket addProduct(Long userId, ProductToBucketDto product) {
        Bucket bucket = getByUserId(userId);
        Package pack = packageRepository.findById(product.getPackId())
                .orElseThrow();

        if (pack.getQuantity() < product.getCount()) {
            throw new ConstraintViolationException("Количество товаров в корзине первышает количество товаров на складе", null);
        }

        if (existsByPack(pack.getId(), bucket.getId())) {
            // TODO
        } else {
            PackageBucket packageBucket = new PackageBucket()
                    .setBucket(bucket)
                    .setPack(pack)
                    .setQuantity(product.getCount());
            bucket.getPackageBuckets().add(packageBucket);
        }
        return bucketRepository.save(bucket);
    }

    public boolean existsByPack(Long packId, Long bucketId) {
        return bucketRepository.existsByPackageBuckets(bucketId, packId);
    }
}
