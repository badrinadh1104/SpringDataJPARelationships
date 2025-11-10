package com.example.student_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	@NotBlank(message = "Street is required")
	@Pattern(regexp = "^[A-Za-z0-9\\s.,'-]+$", message = "Street must contain only valid characters")
	private String street;
	@NotBlank(message = "City is required")
	@Pattern(regexp = "^[A-Za-z\\s]+$", message = "City must contain only letters and spaces")
	private String city;
	@NotBlank(message = "State is required")
	@Pattern(regexp = "^[A-Za-z\\s]+$", message = "State must contain only letters and spaces")
	private String state;
	@NotBlank(message = "Zip code is required")
	@Pattern(regexp = "^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$", message = "Zip code must be in the format A1A 1A1")
	private String zipCode;
	@NotBlank(message = "Country is required")
	@Pattern(regexp = "^[A-Za-z\\s]+$", message = "Country must contain only letters and spaces")
	private String country;
}
