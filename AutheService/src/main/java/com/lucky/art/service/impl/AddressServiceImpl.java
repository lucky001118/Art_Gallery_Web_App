package com.lucky.art.service.impl;

import com.lucky.art.model.Address;
import com.lucky.art.repository.AddressRepository;
import com.lucky.art.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    @Override
    public Address saveAddressOfSeller(Address address) {
        return addressRepository.save(address);
    }
}
