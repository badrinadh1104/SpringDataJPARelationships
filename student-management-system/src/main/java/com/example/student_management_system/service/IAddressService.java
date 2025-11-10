package com.example.student_management_system.service;

import java.util.List;

import com.example.student_management_system.DTO.AddressDTO;
import com.example.student_management_system.model.Address;


public interface IAddressService {
	
	Address createAddress(AddressDTO address);

	Address getAddressById(Long id);

	List<Address> getAllAddresses();

	Address updateAddress(Long id, AddressDTO address);

	void deleteAddress(Long id);

	Address getAddressForStudent(Long studentId);

	void updateStudentAddress(Long studentId, AddressDTO address);

}
