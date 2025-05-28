package com.bite.buddy.controller;

import com.bite.buddy.model.AddressDto;
import com.bite.buddy.model.ApiResponse;
import com.bite.buddy.model.UserDto;
import com.bite.buddy.service.AddressService;
import com.bite.buddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/biteBuddy")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    // POST
    @PostMapping("/user/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("user", userDto);
        UserDto createdUserDto = this.userService.registerUser(requestMap);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping("/user/modify/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("user", userDto);
        requestMap.put("userId", userId);
        UserDto updatedUserDto = this.userService.updateUser(requestMap);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userId);
        this.userService.deleteUser(requestMap);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
    }

    // GET
    @GetMapping("/user/search")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // GET
    @GetMapping("/user/search/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String uid) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", uid);
        return ResponseEntity.ok(this.userService.getUser(requestMap));
    }

    @PostMapping("/user/address/add/{userId}")
    public ResponseEntity<AddressDto> addAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("address", addressDto);
        requestMap.put("userId", userId);
        AddressDto addedAddressDto = this.addressService.addAddress(requestMap);
        return new ResponseEntity<>(addedAddressDto, HttpStatus.CREATED);
    }

    @PutMapping("/user/address/modify/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable String addressId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("address", addressDto);
        requestMap.put("addressId", addressId);
        AddressDto updatedAddressDto = this.addressService.updateAddress(requestMap);
        return new ResponseEntity<>(updatedAddressDto, HttpStatus.OK);
    }

    @DeleteMapping("/user/address/delete/{addressId}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable String addressId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("addressId", addressId);
        this.addressService.deleteAddress(requestMap);
        return ResponseEntity.ok(new ApiResponse("Address deleted successfully", true));
    }

    @GetMapping("/user/address/search/{userId}")
    public ResponseEntity<List<AddressDto>> getAllAddresses(@PathVariable("userId") String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userId);
        return ResponseEntity.ok(this.addressService.getAllAddress(requestMap));
    }
}

