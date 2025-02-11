package com.croustify.backend.repositories;

import com.croustify.backend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    //Extract the userCredentialId from the Customer entity
    Optional<Customer> findByUserCredentialId(Long userCredentialId);

    Customer getReferenceByUserCredentialId(Long id);
}
