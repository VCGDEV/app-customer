package com.example.customer.persistence;

import com.example.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends
        JpaRepository<Customer, String> {
}
