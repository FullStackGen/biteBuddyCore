package com.bite.buddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private List<String> addresses;

    // Constructor
    public User(String userId, String name, String email, String phoneNumber, String password, List<String> addresses) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.addresses = addresses;
    }

    public void displayUserInfo() {
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Address: " + addresses);
    }
}

