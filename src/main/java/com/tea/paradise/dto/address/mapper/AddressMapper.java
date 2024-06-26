package com.tea.paradise.dto.address.mapper;

import com.tea.paradise.dto.address.AddressDto;
import com.tea.paradise.dto.saveDto.AddressSaveDto;
import com.tea.paradise.model.Address;
import com.tea.paradise.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class AddressMapper {

    public abstract AddressDto toDto(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(source = "users", target = "user")
    @Mapping(target = "active", expression = "java(true)")
    public abstract Address toSaveModel(AddressSaveDto addressSaveDto, Users users);
}
