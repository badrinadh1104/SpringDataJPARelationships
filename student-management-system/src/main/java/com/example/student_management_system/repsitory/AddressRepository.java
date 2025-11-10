package com.example.student_management_system.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
