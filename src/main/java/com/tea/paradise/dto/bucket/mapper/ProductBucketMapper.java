package com.tea.paradise.dto.bucket.mapper;

import com.tea.paradise.dto.bucket.ProductBucketDto;
import com.tea.paradise.model.Product;
import org.mapstruct.Mapper;

@Mapper
public abstract class ProductBucketMapper {
    public abstract ProductBucketDto toBucketDto(Product product);
}
