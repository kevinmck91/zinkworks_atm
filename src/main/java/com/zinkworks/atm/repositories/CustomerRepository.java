package com.zinkworks.atm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zinkworks.atm.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	List<Customer> findByAccountNumber(int accountNumber);
	
}
