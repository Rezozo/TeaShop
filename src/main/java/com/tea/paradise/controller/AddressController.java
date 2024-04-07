package com.tea.paradise.controller;

import com.tea.paradise.dto.address.AddressDto;
import com.tea.paradise.dto.address.mapper.AddressMapper;
import com.tea.paradise.dto.saveDto.AddressSaveDto;
import com.tea.paradise.model.Address;
import com.tea.paradise.service.AddressService;
import com.tea.paradise.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
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
    UserService userService;

    @Operation(summary = "Получение всех адресов пользователя")
    @GetMapping
    public List<AddressDto> getAllUserAddresses() {
        return addressService.getUsersAddress().stream()
                .filter(Address::isActive)
                .map(addressMapper::toDto)
                .toList();
    }

    @Operation(summary = "Добавление адреса пользователя")
    @PostMapping
    public AddressDto addUserAddress(@Valid @RequestBody AddressSaveDto saveDto) {
        Address savedAddress = addressService.saveAddress(addressMapper.toSaveModel(saveDto, userService.getLoggedUserInfo()));
        return addressMapper.toDto(savedAddress);
    }

    @Operation(summary = "Удаление адреса пользователя")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserAddress(@PathVariable Long id) {
        addressService.deleteById(id);
        return ResponseEntity.ok("Адрес успешно удалён");
    }
}
