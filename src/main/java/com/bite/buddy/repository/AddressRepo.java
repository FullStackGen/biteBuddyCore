package com.bite.buddy.repository;

import com.bite.buddy.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, String> {

    Optional<Address> findByAddressId(String addressId);

    Optional<List<Address>> findByUser_UserId(String userId);
}
