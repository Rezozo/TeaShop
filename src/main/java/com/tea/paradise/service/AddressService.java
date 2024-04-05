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

    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }

    public Address saveAddress(Address saveModel) {
        return addressRepository.save(saveModel);
    }
}
