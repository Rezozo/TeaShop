package com.tea.paradise.service;

import com.tea.paradise.model.Address;
import com.tea.paradise.repository.AddressRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AddressService {
    AddressRepository addressRepository;
    UserService userService;

    public List<Address> getUsersAddress() {
        return addressRepository.findAllByUser_Id(
                userService.getAuthInfo().getId()
        );
    }

    public Address getById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow();
    }

    public void deleteById(Long id) {
        Address address = getById(id);
        address.setActive(false);
        addressRepository.save(address);
    }

    public Address saveAddress(Address saveModel) {
        return addressRepository.save(saveModel);
    }
}
