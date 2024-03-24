package com.tea.paradise.dto.mapper;

import com.tea.paradise.dto.UserDto;
import com.tea.paradise.enums.UserRole;
import com.tea.paradise.model.Product;
import com.tea.paradise.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class UserMapper {

    @Mapping(source = "favorite", target = "favorites")
    @Mapping(source = "userRole", target = "role")
    public abstract UserDto toDto(Users users,
                                  List<Long> favorites,
                                  UserRole userRole);

    public UserDto map(Users users) {
        List<Long> favorites = users.getFavorites().stream()
                .map(Product::getId)
                .toList();
        UserRole userRole = users.getRole().getTitle(); // TODO bucket mapper, package and etc.
        return toDto(users, favorites, userRole);
    }
}
