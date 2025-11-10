package com.example.student_management_system.DTO;

public record TeacherDTO (
	String firstName,
	String lastName,
	String email,
	String specality,
	String phoneNumber,
	boolean active,
	AddressDTO addressDTO
	){

}
