package com.example.student_management_system.DTO;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record StudentDTO(String firstName, String lastName, String email, LocalDate dateOfBirth, String gender,
		boolean active, String phoneNumber, AddressDTO addressDTO) {

}
