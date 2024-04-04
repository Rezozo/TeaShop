package com.tea.paradise.dto.user.mapper;

import com.tea.paradise.dto.bucket.BucketDto;
import com.tea.paradise.dto.user.UserDto;
import com.tea.paradise.dto.bucket.mapper.BucketMapper;
import com.tea.paradise.enums.UserRole;
import com.tea.paradise.model.Product;
import com.tea.paradise.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public abstract class UserMapper {
    @Autowired
    BucketMapper bucketMapper;

    @Mapping(source = "users.id", target = "id")
    @Mapping(source = "favorites", target = "favorites")
    @Mapping(source = "userRole", target = "role")
    @Mapping(source = "bucket", target = "bucket")
    @Mapping(target = "ordersCount", expression = "java(users.getOrders().size())")
    public abstract UserDto toDto(Users users,
                                  List<Long> favorites,
                                  UserRole userRole,
                                  BucketDto bucket);

    public UserDto map(Users users) {
        List<Long> favorites = users.getFavorites().stream()
                .map(Product::getId)
                .toList();
        UserRole userRole = users.getRole().getTitle();
        BucketDto bucketDto = bucketMapper.map(users.getBucket());
        return toDto(users, favorites, userRole, bucketDto);
    }
}
