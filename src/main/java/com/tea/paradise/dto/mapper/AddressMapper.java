package com.tea.paradise.dto.mapper;

import com.tea.paradise.dto.AddressDto;
import com.tea.paradise.model.Address;
import org.mapstruct.Mapper;

@Mapper
public abstract class AddressMapper {

    public abstract AddressDto toDto(Address address);
}
