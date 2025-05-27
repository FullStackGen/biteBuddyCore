package com.bite.buddy.model;

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
    private List<String> addresses;
}
