package com.example.student_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.student_management_system.DTO.AddressDTO;
import com.example.student_management_system.exceptions.AddressNotFoundException;
import com.example.student_management_system.exceptions.StudentNotFoundException;
import com.example.student_management_system.model.Address;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.repsitory.AddressRepository;
import com.example.student_management_system.repsitory.StudentRepository;

@Service
public class AddressServiceImpl implements IAddressService {
	
	private AddressRepository addressRepository;
	private StudentRepository studentRepository;
	
	public AddressServiceImpl(AddressRepository addressRepository, StudentRepository studentRepository) {
		this.addressRepository = addressRepository;
		this.studentRepository = studentRepository;
	}
	
	
	@Override
	public Address createAddress(AddressDTO address) {
		Address newAddress = Address.builder()
				.street(address.street())
				.city(address.city())
				.state(address.state())
				.zipCode(address.zipCode())
				.country(address.country())
				.build();
		return addressRepository.save(newAddress);
	}

	@Override
	public Address getAddressById(Long id) {
		return addressRepository.findById(id).orElse(null);
	}

	@Override
	public List<Address> getAllAddresses() {
		return addressRepository.findAll();
	}

	@Override
	public Address updateAddress(Long id, AddressDTO address) {
		Address existingAddress = addressRepository.findById(id).orElseThrow(()-> new AddressNotFoundException("Address not found with id: " + id));
		existingAddress.setStreet(address.street());
		existingAddress.setCity(address.city());
		existingAddress.setState(address.state());
		existingAddress.setZipCode(address.zipCode());
		existingAddress.setCountry(address.country());
		return addressRepository.save(existingAddress);
	}

	@Override
	public void deleteAddress(Long id) {
		Address existingAddress = addressRepository.findById(id).orElseThrow(()-> new AddressNotFoundException("Address not found with id: " + id));	
		addressRepository.delete(existingAddress);
		
		
	}

	@Override
	public Address getAddressForStudent(Long studentId) {
		Student student = studentRepository.findById(studentId).orElseThrow(()-> new StudentNotFoundException("Student not found with id: " + studentId));
		return student.getAddress();
	}

	@Override
	public void updateStudentAddress(Long studentId, AddressDTO address) {
		Student student = studentRepository.findById(studentId).orElseThrow(()-> new StudentNotFoundException("Student not found with id: " + studentId));
		Address existingAddress = student.getAddress();
		existingAddress.setStreet(address.street());
		existingAddress.setCity(address.city());
		existingAddress.setState(address.state());
		existingAddress.setZipCode(address.zipCode());
		existingAddress.setCountry(address.country());
		addressRepository.save(existingAddress);
		
		
	}

}
