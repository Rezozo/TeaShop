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

import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.Objects;

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
            bucket.getPackageBuckets().stream()
                    .filter(packageBucket -> Objects.equals(packageBucket.getPack().getId(), pack.getId()) &&
                            Objects.equals(packageBucket.getBucket().getId(), bucket.getId()))
                    .findFirst()
                    .map(packageBucket -> packageBucket.setQuantity(product.getCount()))
                    .orElseThrow();
        } else {
            PackageBucket packageBucket = new PackageBucket()
                    .setBucket(bucket)
                    .setPack(pack)
                    .setQuantity(product.getCount());
            bucket.getPackageBuckets().add(packageBucket);
        }
        bucket.setModifiedDate(ZonedDateTime.now());

        return bucketRepository.save(bucket);
    }

    public boolean existsByPack(Long packId, Long bucketId) {
        return bucketRepository.existsByPackageBuckets(bucketId, packId);
    }

    public Bucket deletePackById(Long userId, Long packId) {
        Bucket bucket = getByUserId(userId);
        bucket.getPackageBuckets().remove(bucket.getPackageBuckets().stream()
                .filter(packageBucket -> packageBucket.getPack().getId().equals(packId))
                .findFirst()
                .orElseThrow(() -> new ConstraintViolationException(
                        MessageFormat.format("Упаковки с id: {0} не существует", packId), null)
                )
        );
        bucket.setModifiedDate(ZonedDateTime.now());

        return bucketRepository.save(bucket);
    }

    public Bucket clear(Long userId) {
        Bucket bucket = getByUserId(userId);
        bucket.getPackageBuckets().clear();
        bucket.setModifiedDate(ZonedDateTime.now());

        return bucketRepository.save(bucket);
    }
}
