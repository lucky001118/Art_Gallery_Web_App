package com.lucky.art.controller;

import com.lucky.art.model.Address;
import com.lucky.art.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    @PostMapping("/save")
    public ResponseEntity<Address> saveAddressOfSeller(@RequestBody Address address){
        Address createdAddress = addressService.saveAddressOfSeller(address);
        return new ResponseEntity<>(createdAddress,HttpStatus.CREATED);
    }

    @PostMapping("/order/save")
    public ResponseEntity<Address> saveAddressOfOrder(@RequestBody Address address){
        Address createdAddress = addressService.saveAddressOfSeller(address);
        return new ResponseEntity<>(createdAddress,HttpStatus.CREATED);
    }
}
