package com.tea.paradise.dto.bucket.mapper;

import com.tea.paradise.dto.bucket.BucketDto;
import com.tea.paradise.dto.bucket.PackageBucketDto;
import com.tea.paradise.model.Bucket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Optional;

@Mapper
public abstract class BucketMapper {

    @Value("${tea.bonuses-percent}")
    private double bonusPercent;

    @Autowired
    PackageBucketMapper packageBucketMapper;

    @Mapping(source = "products", target = "products")
    @Mapping(source = "bucket.user.id", target = "userId")
    public abstract BucketDto toDto(Bucket bucket,
                                    List<PackageBucketDto> products,
                                    Double totalSumWithDiscount,
                                    Double totalDiscount,
                                    Integer plusTeaBonuses);

    public BucketDto map(Bucket bucket) {
        List<PackageBucketDto> packageFullDtoList = bucket.getPackageBuckets().stream()
                .map(aPackage -> packageBucketMapper.mapBucketDto(aPackage))
                .toList();
        double totalSum = 0.0;
        double totalDiscount = 0.0;
        for (PackageBucketDto packageBucketDto : packageFullDtoList) {
            double sum = packageBucketDto.getPrice() * packageBucketDto.getQuantityInBucket();
            double discount = Optional.ofNullable(packageBucketDto.getProduct().getDiscount())
                    .map(d -> sum * (d / 100.0))
                    .orElse(0.0);
            totalDiscount += discount;
            totalSum += sum - discount;
        }
        Integer plusBonuses = (int) (totalSum * bonusPercent);
        return toDto(bucket, packageFullDtoList, totalSum, totalDiscount, plusBonuses);
    }
}
