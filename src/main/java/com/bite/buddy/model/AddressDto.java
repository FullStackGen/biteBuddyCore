package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AddressDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String addressId;
    private String city;
    private String zip;
    private String state;
    private String locality;
    private String userId;
    private boolean defaultAddress;
}