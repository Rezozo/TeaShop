package com.tea.paradise.dto.bucket.mapper;

import com.tea.paradise.dto.bucket.PackageBucketDto;
import com.tea.paradise.dto.bucket.ProductBucketDto;
import com.tea.paradise.dto.product.mapper.ProductMapper;
import com.tea.paradise.dto.variant.mapper.VariantMapper;
import com.tea.paradise.model.PackageBucket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Mapper
public abstract class PackageBucketMapper {
    @Autowired
    VariantMapper variantMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductBucketMapper productBucketMapper;
    @Value("${tea.bonuses-percent}")
    private double bonusPercent;

    @Mapping(source = "packageBucket.pack.id", target = "packId")
    @Mapping(source = "packageBucket.pack.variant", target = "variant")
    @Mapping(source = "packageBucket.pack.price", target = "price")
    @Mapping(source = "product", target = "product")
    @Mapping(source = "packageBucket.quantity", target = "quantityInBucket")
    @Mapping(source = "plusTeaBonuses", target = "plusTeaBonuses")
    public abstract PackageBucketDto toBucketDto(PackageBucket packageBucket,
                                                 ProductBucketDto product,
                                                 double plusTeaBonuses);

    public PackageBucketDto mapBucketDto(PackageBucket packageBucket) {
        ProductBucketDto productBucketDto = productBucketMapper.toBucketDto(packageBucket.getPack().getProduct());
        int plusTeaBonuses = BigDecimal.valueOf(Optional.ofNullable(productBucketDto.getDiscount())
                .map(d -> ((packageBucket.getPack().getPrice() * packageBucket.getQuantity()) * ((100.0 - d) / 100.0)) * bonusPercent)
                .orElse((packageBucket.getPack().getPrice() * packageBucket.getQuantity()) * bonusPercent))
                .setScale(2, RoundingMode.HALF_UP).intValue();
        return toBucketDto(packageBucket, productBucketDto, plusTeaBonuses);
    }
}
