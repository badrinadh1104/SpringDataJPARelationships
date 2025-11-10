	package com.example.student_management_system.DTO;

import lombok.Builder;

@Builder
public record AddressDTO(String street, String city, String state, String zipCode, String country) {

}
