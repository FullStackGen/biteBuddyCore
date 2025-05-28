package com.bite.buddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    private String addressId;
    private String city;
    private String zip;
    private String state;
    private String locality;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

