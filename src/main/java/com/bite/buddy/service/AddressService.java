package com.bite.buddy.service;

import com.bite.buddy.model.AddressDto;

import java.util.List;
import java.util.Map;

public interface AddressService {

    AddressDto addAddress(Map<String, Object> requestMap);

    AddressDto updateAddress(Map<String, Object> requestMap);

    List<AddressDto> getAddressesByUser(Map<String, Object> requestMap);

    void deleteAddress(Map<String, Object> requestMap);
}