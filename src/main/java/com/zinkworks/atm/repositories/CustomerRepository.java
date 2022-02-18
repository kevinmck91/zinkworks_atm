package com.zinkworks.atm.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zinkworks.atm.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	List<Customer> findByAccountNumber(int accountNumber);

}
