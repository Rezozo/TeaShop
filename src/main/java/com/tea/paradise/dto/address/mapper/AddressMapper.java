package com.tea.paradise.dto.address.mapper;

import com.tea.paradise.dto.address.AddressDto;
import com.tea.paradise.model.Address;
import org.mapstruct.Mapper;

@Mapper
public abstract class AddressMapper {

    public abstract AddressDto toDto(Address address);
}
