package com.zinkworks.atm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zinkworks.atm.dto.Atm;
import com.zinkworks.atm.dto.Customer;

public interface AtmRepository extends JpaRepository<Atm, Integer>{

}
