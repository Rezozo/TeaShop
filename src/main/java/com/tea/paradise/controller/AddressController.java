package com.tea.paradise.controller;

import com.tea.paradise.dto.address.AddressDto;
import com.tea.paradise.dto.address.mapper.AddressMapper;
import com.tea.paradise.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Операции с адресами")
@RestController
@RequestMapping("/address")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddressController {
    AddressMapper addressMapper;
    AddressService addressService;

    @Operation(summary = "Получение всех адресов пользователя")
    @GetMapping
    public List<AddressDto> getAllUserAddresses() {
        return addressService.getUsersAddress().stream()
                .map(addressMapper::toDto)
                .toList();
    }

    @Operation(summary = "Добавление адреса пользователя")
    @PostMapping
    public AddressDto addUserAddress() { // TODO add save dto
        return null;
    }

    @Operation(summary = "Удаление адреса пользователя")
    @DeleteMapping("{id}")
    public void deleteUserAddress(@PathVariable Long id) {

    }

}
