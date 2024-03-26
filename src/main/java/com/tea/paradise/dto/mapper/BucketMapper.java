package com.tea.paradise.dto.mapper;

import com.tea.paradise.dto.BucketDto;
import com.tea.paradise.dto.PackageFullDto;
import com.tea.paradise.model.Bucket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public abstract class BucketMapper {
    @Autowired
    PackageMapper packageMapper;

    @Mapping(source = "packages", target = "packages")
    public abstract BucketDto toDto(Bucket bucket,
                                    List<PackageFullDto> packages);

    public BucketDto map(Bucket bucket) {
        List<PackageFullDto> packageFullDtoList = bucket.getPackages().stream()
                .map(aPackage -> packageMapper.mapFullDto(aPackage, bucket.getUser().getId()))
                .toList();
        return toDto(bucket, packageFullDtoList);
    }
}
