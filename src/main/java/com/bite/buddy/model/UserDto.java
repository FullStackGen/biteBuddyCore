package com.bite.buddy.model;

import com.bite.buddy.entity.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;
    private List<Address> addresses;
}
