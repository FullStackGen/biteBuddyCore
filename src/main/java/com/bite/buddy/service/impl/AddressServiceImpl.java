package com.bite.buddy.service.impl;

import com.bite.buddy.entity.Address;
import com.bite.buddy.entity.User;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.model.AddressDto;
import com.bite.buddy.repository.AddressRepo;
import com.bite.buddy.repository.UserRepo;
import com.bite.buddy.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final ModelMapper modelMapper;
    @Autowired
    AddressRepo addressRepo;
    @Autowired
    private UserRepo userRepo;

    public AddressServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AddressDto addAddress(Map<String, Object> requestMap) {
        AddressDto addressDto = (AddressDto) requestMap.get("address");
        String userId = requestMap.get("userId").toString();
        Address address = modelMapper.map(addressDto, Address.class);
        UUID id = UUID.randomUUID();
        long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
        address.setAddressId("AD." + n);
        User user = this.userRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        address.setUser(user);
        Address savedAddress = addressRepo.save(address);
        return modelMapper.map(savedAddress, AddressDto.class);
    }

    @Override
    public AddressDto updateAddress(Map<String, Object> requestMap) {
        AddressDto addressDto = (AddressDto) requestMap.get("address");
        String addressId = requestMap.get("addressId").toString();
        Address address = this.addressRepo.findByAddressId(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
        address.setCity(addressDto.getCity());
        address.setZip(addressDto.getZip());
        address.setState(addressDto.getState());
        address.setLocality(addressDto.getLocality());
        Address savedAddress = addressRepo.save(address);
        return modelMapper.map(savedAddress, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAllAddress(Map<String, Object> requestMap) {
        String userId = requestMap.get("userId").toString();
        List<Address> addresses = this.addressRepo.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", userId));
        return addresses.stream().map(address -> this.modelMapper.map(address, AddressDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteAddress(Map<String, Object> requestMap) {
        String addressId = requestMap.get("addressId").toString();
        Address address = this.addressRepo.findByAddressId(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
        this.addressRepo.delete(address);
    }
}
