package com.example.student_management_system.exceptions;

public class TeacherNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TeacherNotFoundException(String message) {
		super(message);
	}

}
