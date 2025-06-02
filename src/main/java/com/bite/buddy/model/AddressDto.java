package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private String addressId;
    private String city;
    private String zip;
    private String state;
    private String locality;
    private String userId;
    private boolean isDefault;
}
