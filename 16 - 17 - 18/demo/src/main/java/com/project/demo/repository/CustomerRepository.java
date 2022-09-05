package com.project.demo.repository;

import com.project.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByName(String name);

    Optional<Customer> findCustomerByNameIgnoreCase(String name);

}
