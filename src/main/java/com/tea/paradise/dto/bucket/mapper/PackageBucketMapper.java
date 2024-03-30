package com.tea.paradise.dto.bucket.mapper;

import com.tea.paradise.dto.bucket.PackageBucketDto;
import com.tea.paradise.dto.bucket.ProductBucketDto;
import com.tea.paradise.dto.product.mapper.ProductMapper;
import com.tea.paradise.dto.variant.mapper.VariantMapper;
import com.tea.paradise.model.PackageBucket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class PackageBucketMapper {
    @Autowired
    VariantMapper variantMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductBucketMapper productBucketMapper;

    @Mapping(source = "packageBucket.pack.id", target = "packId")
    @Mapping(source = "packageBucket.pack.variant", target = "variant")
    @Mapping(source = "packageBucket.pack.price", target = "price")
    @Mapping(source = "product", target = "product")
    @Mapping(source = "packageBucket.quantity", target = "quantityInBucket")
    public abstract PackageBucketDto toBucketDto(PackageBucket packageBucket,
                                                 ProductBucketDto product);

    public PackageBucketDto mapBucketDto(PackageBucket packageBucket) {
        ProductBucketDto productBucketDto = productBucketMapper.toBucketDto(packageBucket.getPack().getProduct());
        return toBucketDto(packageBucket, productBucketDto);
    }
}
