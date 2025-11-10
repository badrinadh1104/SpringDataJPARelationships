# Spring-Boot Project (STUDENT-MANAGEMENT)

Today we are going to learn and implement a Spring Boot application. There are two types of applications: monolithic applications and microservice-based applications. For simplicity and to avoid complexity, we will create a monolithic app first and then convert it into a microservice-based application. 


## Creating Spring Boot Application

There are multiple ways to create a Spring Boot application:

1. Using Spring Initializr
2. Using a Spring Tool Suite IDE like Eclipse
3. Using IntelliJ IDEA, which has a built-in option to create Spring Boot apps

Create a Maven-based Spring Boot application with the following configuration:

- **Application name:** student-management-system
- **Package name:** com.student-management-system
- **Starter dependencies:**
    1. Spring Starter Web
    2. Spring Dev Tools
    3. Spring Data JPA
    4. MySQL Connector
    5. Validation
    6. Lombok
    7. springdoc-openapi-starter-webmvc-ui (Swagger API)

Ensure these dependencies are included in your pom.xml file. 

```jsx
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.5.7</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.student-management-system</groupId>
	<artifactId>student-management-system</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>student-management-system</name>
	<description>Demo project for Spring Boot student-management-system</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>17</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.restdocs</groupId>
			<artifactId>spring-restdocs-mockmvc</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
		    <groupId>org.springdoc</groupId>
		    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
		    <version>2.6.0</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.asciidoctor</groupId>
				<artifactId>asciidoctor-maven-plugin</artifactId>
				<version>2.2.1</version>
				<executions>
					<execution>
						<id>generate-docs</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>process-asciidoc</goal>
						</goals>
						<configuration>
							<backend>html</backend>
							<doctype>book</doctype>
						</configuration>
					</execution>
				</executions>
				<dependencies>
					<dependency>
						<groupId>org.springframework.restdocs</groupId>
						<artifactId>spring-restdocs-asciidoctor</artifactId>
						<version>${spring-restdocs.version}</version>
					</dependency>
				</dependencies>
			</plugin>
		</plugins>
	</build>

</project>

```

## Adding Properties

```java
spring.application.name=student-management-system
server.port = 8080
spring.datasource.url=jdbc:mysql://localhost:3306/student_db?CreateDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

Add this properties in your [application.properties](http://application.properties) file .

## Creating Modal Classes

You now have all the dependencies needed to build your student-management-system application. Next, create a model class for Student. First, create a package called `model`, then inside that package create a Java class called `Student.java`.

```jsx
package com.example.student_management_system.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentId;
	@NotBlank(message = "First name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
	private String firstName;
	@NotBlank(message = "Last name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
	private String lastName;
	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;
	@NotNull(message = "Date of birth is required")
	@Past(message = "Date of birth must be in the past")
	private LocalDate dateOfBirth;
	@NotBlank(message = "gender is required")
	private String gender;
	@NotNull(message = "enrollement date is required")
	private LocalDate enrollmentDate;
	@NotNull(message = "Active status is required")
	private boolean active;
	@NotBlank(message = "Phone number is required")
	private String phoneNumber;
	@PrePersist
	public void addEnrollmentDate() {
		this.enrollmentDate = LocalDate.now();
	}
}

```

This is the [Student.java](http://Student.java) class. Let's discuss the annotations used: 

**1. @Entity:** This annotation at the top of the class creates a table in the database. It comes from `jakarta.persistence.Entity`.

**2. @Id:** This marks the field as a primary key in the database table. A primary key is a unique identifier for each record.

**3. @GeneratedValue:** This generates values in incremental order automatically.

**4. Validation annotations:**
We have

`@NotEmpty` — The field cannot be empty.

`@NotNull` — The field cannot be null.

`@NotBlank` — The field cannot be null or empty.

`@Size(min=X, max=Y)` — Validates that a String, Collection, Map, or array falls within the specified size range.

`@Min(value)` — Ensures a number is greater than or equal to the specified minimum.

`@Max(value)` — Ensures a number is less than or equal to the specified maximum.

`@DecimalMin(value)` — Ensures a decimal number is greater than or equal to the specified minimum.

`@DecimalMax(value)` — Ensures a decimal number is less than or equal to the specified maximum.

`@Pattern(regexp)` — Validates a String against a regular expression.

`@Email` — Ensures a String is a valid email address.

`@URL` — Ensures a String is a valid URL.

`@AssertTrue` — Validates that a boolean field is true.

`@AssertFalse` — Validates that a boolean field is false.

creating Address.java

```jsx
package com.example.student_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

```

Before moving ahead, let's learn about regular expressions. We're using regexp to validate the fields, but do you know how they work and how they should be written?

| **Metacharacter** | **Matches** | **Example** |
| --- | --- | --- |
| . | Any character except \n | a.cmatchesabc,a7c |
| * | 0 or more repetitions | lo*matchesl,lo,loo |
| + | 1 or more repetitions | lo+matcheslo,loo |
| ? | 0 or 1 repetition | colou?rmatchescolor,colour |
| [abc] | One ofa,b, orc | [ch]atmatchescatorhat |
| [^abc] | NOTa,b, orc | [^0-9]matches a non-digit |
| (group) | A group for extraction | (abc)+ |
| ` | ` | OR between patterns |
| ^ | Start of string | ^Thematches "The" at beginning |
| $ | End of string | end$matches "end" at end |

`\w` is shorthand for `[A-Za-z0-9_]`
`\d` is shorthand for `[0-9]`
`\*` means a literal `*`, not "0 or more"
`\.` means a literal `.`, not "any character"
The backslash **`\`** is a *special escape character* in regular expressions.

**Example:** `badrinadh.ande@gmail.com`
`^[\w\.]+@[\w\.]+\.[a-z]{2,}$`

Breaking it down:
`^` — Start of the string
`[\w\.]+` — Any word character or period, repeated one or more times
`@` — A single `@` symbol
`[\w\.]+` — Any word character or period, repeated one or more times
`\.` — A literal period
`[a-z]{2,}` — Two or more lowercase letters
`$` — End of the string

Now we have some idea of regex, but keep learning and practicing to deepen your understanding.

# 1. Adding one to one relation

Every student will have one address. Here we can add a one-to-one unidirectional relationship.

```java
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "address_id", referencedColumnName = "addressId")
private Address address;
```

Add these lines to the [Student.java](http://Student.java) class. This creates a one-to-one relationship between student and address.

@JoinColumn is an annotation used to add a new column in the table created for the entity. The `name` attribute provides the column name, and `referencedColumnName` is the name of the foreign key column from the referenced table (here, it's the Address table).

Now let's create a Teacher class with a one-to-one relationship with the Address class.

```java
package com.example.student_management_system.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teacherId;
	@NotBlank(message = "First name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
	private String firstName;
	@NotBlank(message = "Last name is required")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
	private String lastName;
	@NotBlank(message = "Email is required")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email should be valid")
	private String email;
	@NotBlank(message = "Speciality is required")
	private String speaciality;
	@NotNull(message = "Hire date is required")
	private LocalDate hireDate;
	@NotBlank(message = "Phone number is required")
	private String phoneNumber;
	@NotBlank(message = "Active status is required")
	private boolean active;
	@NotBlank(message = "Address is required")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "addressId")
	private Address address;	
	

}

```

Lets create a class for Course (Course.java)

```java
package com.example.student_management_system.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseId;
	private String courseName;
	private String courseDescription;
	private int credits;
	private LocalDate startDate;
	private LocalDate endDate;
}

```

# 2. Many to Many Relation

We've now created three classes: Student, Teacher, and Course.

Each student can enroll in many courses, and each course can have many students enrolled. This demonstrates a many-to-many relationship between Student and Course.

The same pattern applies to teachers. Many teachers can teach many courses, and many courses can be taught by many teachers. This also represents a many-to-many relationship—this time between Teacher and Course.

Lets Add Relations to our Classes .

In the Student Class  :

```java
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
		name = "student_courses",
		joinColumns = @JoinColumn(name = "student_id"),
		inverseJoinColumns = @JoinColumn(name = "course_id")
	)
	private List<Course> courses;
	
	public void enrollInCourse(Course course) {
		if (this.courses == null) {
			this.courses = new ArrayList<>();
		}
		this.courses.add(course);

	}
```

In the Teacher Class :

```java
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
		name = "teacher_courses",
		joinColumns = @JoinColumn(name = "teacher_id"),
		inverseJoinColumns = @JoinColumn(name = "course_id")
	)
	private List<Course> courses;
	
	public void addCourse(Course course) {
		if (this.courses == null) {
			this.courses = new ArrayList<>();
		}
		this.courses.add(course);
	}
```

In the  Course Class  :

```java
@ManyToMany(mappedBy = "courses")
private List<Teacher> teachers;
@ManyToMany(mappedBy = "courses")
private List<Student> students;

```

We've added some code to achieve the many-to-many relation, but let's understand some terms we've used so far.

Cascade in JPA/Hibernate means that when you perform an operation (like save, delete, or update) on one object, the same operation automatically applies to its related objects.

For example, if you save a Teacher with a list of Courses and the cascade type is set to PERSIST, JPA automatically saves both the Teacher and all associated Courses. This keeps related data in sync without requiring you to manually handle each related entity.

Lets Create Departament.java

```java
package com.example.student_management_system.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
	private Long departmentId;
	private String departmentName;
	private String departmentCode;
	private String description;

}

```

In the Department class, one department has many students—the same applies to teachers and courses. From the Student class perspective, many students belong to one department—again, the same for Teacher and Course. So we'll implement a one-to-many relationship in the Department class for Student, Course, and Teacher, and a many-to-one relationship with Department from Student, Teacher, and Course.

Add this Changes in Student.java 

```java
@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
@JoinColumn(name = "department_id")
private Department department;
```

Add this Changes in Teacher.java 

```java
@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
@JoinColumn(name = "department_id")
private Department department;
```

Add this Changes in Course.java 

```java
@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
@JoinColumn(name = "department_id")
private Department department;
```

Now, let's create a Review class. Review has a many-to-one relationship with Student, Course, and Teacher.

```java
package com.example.student_management_system.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	@NotBlank(message = "Rating is required")
	private int rating;
	@NotBlank(message = "Comment is required")
	private String comment;
	@NotBlank(message = "Review date is required")
	private LocalDateTime reviewDate;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "student_id")
	private Student student;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "course_id")
	private Course course;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	@PrePersist
	public void setReviewDate() {
		this.reviewDate = LocalDateTime.now();
	}
}

```

You're now all set with the model classes and table creation.

This file contains the database diagram showing the relationships among the different tables created in the database.

[Student_management_database.pdf](Spring-Boot%20Project%20(STUDENT-MANAGEMENT)%202a3ea0dad24580b9b296ff810cb8e175/Student_management_database.pdf)

# 3. Adding Repository Layer

Create a new package: `com.example.student_management_system.repository` 

Add the following classes to this package.

In the repository layer, we create interfaces that extend JPA Repository. This allows us to use predefined methods with built-in queries.

In StudentRepository.java interface

```java
package com.example.student_management_system.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
```

In TeacherRepository.java interface

```java
package com.example.student_management_system.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}

```

In CourseRepository.java interface

```java
package com.example.student_management_system.repsitory;

import com.example.student_management_system.StudentManagementSystemApplication;
import com.example.student_management_system.model.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}

```

In AddressRepository.java interface

```java
package com.example.student_management_system.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}

```

In DepartmentRepository.java interface

```java
package com.example.student_management_system.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}

```

In ReviewRepository.java interface

```java
package com.example.student_management_system.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.student_management_system.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}

```

# 4. Service Layer

Before We begin the service layer , lets first write the Interfaces with all the methods to implement for each service .

## 1. StudentService Interface

**Purpose:** Manages student data and operations.

## Methods:

- **`Student createStudent(Student student)`**
    - Adds a new student to the system.
- **`Student getStudentById(Long id)`**
    - Returns a student by their unique identifier.
- **`List<Student> getAllStudents()`**
    - Lists all students.
- **`Student updateStudent(Long id, Student student)`**
    - Updates data for an existing student.
- **`void deleteStudent(Long id)`**
    - Removes a student from the system.
- **`List<Course> getEnrolledCourses(Long studentId)`**
    - Returns courses the student is enrolled in.
- **`void enrollStudentInCourse(Long studentId, Long courseId)`**
    - Enrolls a student in a course.
- **`void removeStudentFromCourse(Long studentId, Long courseId)`**
    - Unenrolls a student.

---

## 2. CourseService Interface

**Purpose:** Handles course data and enrollment logic.

## Methods:

- **`Course createCourse(CourseDTO course)`**
    - Adds a new course.
- **`Course getCourseById(Long id)`**
    - Get details for a specific course.
- **`List<Course> getAllCourses()`**
    - List all courses.
- **`Course updateCourse(Long id, Course course)`**
    - Update a course's information.
- **`void deleteCourse(Long id)`**
    - Remove a course.
- **`List<Student> getEnrolledStudents(Long courseId)`**
    - List students enrolled in a specific course.
- **`List<Teacher> getAssignedTeachers(Long courseId)`**
    - List teachers assigned to a course.
- **`void assignTeacherToCourse(Long courseId, Long teacherId)`**
    - Assign a teacher.
- **`void removeTeacherFromCourse(Long courseId, Long teacherId)`**
    - Unassign a teacher.

---

## 3. TeacherService Interface

**Purpose:** Manages teacher information and teaching assignments.

## Methods:

- **`Teacher createTeacher(TeacherDTO teacher)`**
- **`Teacher getTeacherById(Long id)`**
- **`List<Teacher> getAllTeachers()`**
- **`Teacher updateTeacher(Long id, TeacherDTO teacher)`**
- **`void deleteTeacher(Long id)`**
- **`List<Course> getCoursesTaught(Long teacherId)`**
- **`void assignCourseToTeacher(Long teacherId, Long courseId)`**
- **`void removeCourseFromTeacher(Long teacherId, Long courseId)`**

---

## 4. DepartmentService Interface

**Purpose:** Organizes departments and their associated entities.

## Methods:

- **`Department createDepartment(DepartmentDTO department)`**
- **`Department getDepartmentById(Long id)`**
- **`List<Department> getAllDepartments()`**
- **`Department updateDepartment(Long id, DepartmentDTO department)`**
- **`void deleteDepartment(Long id)`**
- **`List<Student> getStudentsInDepartment(Long departmentId)`**
- **`List<Teacher> getTeachersInDepartment(Long departmentId)`**
- **`List<Course> getCoursesInDepartment(Long departmentId)`**
- `List<Course> getCoursesInDepartment(Long departmentId);`

`List<Student> getStudentsInDepartment(Long departmentId);`

`List<Teacher> getTeachersInDepartment(Long departmentId);`

**`void** assignTeacherToDepartment(Long teacherId, Long departmentId);`

**`void** assignStudentToDepartment(Long studentId, Long departmentId);`

**`void** assignCourseToDepartment(Long courseId, Long departmentId);`

---

## 5. AddressService Interface

**Purpose:** Manages address records, often linked to students.

## Methods:

- **`Address createAddress(AddressDTO address)`**
- **`Address getAddressById(Long id)`**
- **`List<Address> getAllAddresses()`**
- **`Address updateAddress(Long id, AddressDTO address)`**
- **`void deleteAddress(Long id)`**
- **`Address getAddressForStudent(Long studentId)`**
- **`void updateStudentAddress(Long studentId, AddressDTO address)`**

---

## 6. ReviewService Interface

**Purpose:** Handles all course reviews submitted by students.

## Methods:

- **`Review createReview(ReviewDTO review)`**
- **`Review getReviewById(Long id)`**
- **`List<Review> getReviewsForCourse(Long courseId)`**
- **`List<Review> getReviewsByStudent(Long studentId)`**
- **`Review updateReview(Long id, ReviewDTO review)`**
- **`void deleteReview(Long id)`**

# Data Transfer Objects (DTOs)

## What Is a DTO?

A **Data Transfer Object (DTO)** is a simple object that holds data and is used to transfer information between different layers of an application. DTOs contain only the data needed for a particular operation — no business logic or database access code. They act as containers for the information you're sending to or receiving from a service, especially in web APIs.

## Purpose and Advantages of Using DTOs

- **Separation of concerns:** DTOs keep your internal model (domain objects/entities) separate from what you expose externally through APIs. This makes your code cleaner, more organized, and less error-prone.
- **Control over exposed data:** You can choose exactly what data is shared, hiding sensitive details or complex internal fields.
- **Simpler, safer data exchange:** Clients and other parts of your app get only what they need, not your whole database object with extra details or links.
- **Versioning and flexibility:** Changing DTOs over time is easier, so you can evolve your APIs without changing your core business logic.

## Records as DTOs: Why They're Great

In Java, using a `record` for DTOs brings extra advantages:

- **Immutability:** Java records are immutable—all fields are final, and their values cannot be changed after creation. This prevents accidental changes and makes your code safer, especially in multi-threaded environments.
- **Concise syntax:** Records require less code—no need to write getters, setters, `equals`, `hashCode`, or `toString`, as all are generated automatically.
- **Clear intent:** Using records for DTOs signals that these objects are meant only for carrying data.

## Code Examples & Explanations

Here are some practical code snippets from your project showing how DTOs are defined and used. Each snippet is followed by a plain English description.

---

## Example 1: AddressDTO

`javapackage com.example.student_management_system.DTO;

import lombok.Builder;

@Builder
public record AddressDTO(
    String street, 
    String city, 
    String state, 
    String zipCode, 
    String country
) {}`

**Explanation:**

- This DTO describes an address. Each field (street, city, state, zip code, country) is a piece of information about the student's location.
- Using `@Builder` from Lombok makes it easy to create an AddressDTO using the builder pattern (useful for objects with many fields).
- This record is concise, immutable, and only exists for data transfer.

---

## Example 2: StudentDTO

`javapackage com.example.student_management_system.DTO;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record StudentDTO(
    String firstName,
    String lastName,
    String email,
    LocalDate dateOfBirth,
    String gender,
    boolean active,
    String phoneNumber,
    AddressDTO addressDTO
) {}`

**Explanation:**

- This DTO includes basic student details: name, email, birth date, gender, status, phone, and address.
- The `AddressDTO` field shows how to nest another DTO, passing structured data between layers.
- All the fields are meant for external exchange (e.g., through an API), not for internal logic.

---

## Example 3: CourseDTO

`javapackage com.example.student_management_system.DTO;

public record CourseDTO(
    String courseName,
    String courseDescription,
    int credits
) {}`

**Explanation:**

- This DTO defines the critical information about a course, omitting complex relationships or internal fields.
- It's simple and focused on transportable data.

---

## Example 4: TeacherDTO

`javapackage com.example.student_management_system.DTO;

public record TeacherDTO(
    String firstName,
    String lastName,
    String email,
    String specality,
    String phoneNumber,
    boolean active,
    AddressDTO addressDTO
) {}`

**Explanation:**

- All relevant details about a teacher are included—contact info, specialty, active status, and address.
- Because address is stored with its own DTO, the structure avoids exposing internal relational links.

---

## Example 5: ReviewDTO and CourseReviewDTO

`javapublic record ReviewDTO(
    int rating,
    String comment,
    Long studentId,
    Long teacherId,
    Long courseId
) {}`

**Explanation:**

- This DTO collects feedback by storing ratings, comments, and references to related entities.
- It does **not** expose any complex objects—just the IDs, which keeps data transfer efficient and less error-prone.

---

## How DTOs Differ from Entities or Domain Objects

- **Entities/domain objects:** Tie directly to your database and may include business logic, validation, or behavior. Changing these impacts the database and core logic.
- **DTOs:** Solely for moving data; no logic, validation, or database hooks. Can be shaped, evolved, or versioned with less risk to core functions.

## Scenarios Where DTOs Shine

- **Web APIs:** Sending only needed fields to clients (e.g., a StudentDTO for displaying student profiles).
- **Security:** Omitting sensitive fields (like passwords) from DTOs.
- **Decoupling:** Evolving your API or internal objects independently.
- **Testing and Mocking:** DTOs make demo data simple to prepare and pass around.
- **Mapping and Transformation:** Easy to convert data between formats or layers (using mapping libraries or manual code).

## Practical Usage: From Controller to Service

Suppose you receive a POST request to add a new student. The request payload is mapped to a `StudentDTO`, which:

- Is validated by your controller.
- Passed to your service layer for business logic.
- Eventually converted to an entity for database persistence (using a mapper).
- When retrieving, your entity is mapped back to a `StudentDTO` so the client gets only the proper fields.

# 5. RestController Layer Explained

The **RestController** layer in a Spring Boot application is the gateway between users (or other systems) and the backend logic. It listens for HTTP requests (like GET or POST) and tells the Service layer what to do with each one. This layer is a central point for processing data, managing application operations, and returning results—often as JSON—while keeping the app secure and organized.

## How RestControllers Work

- **Listening for Requests:** Each RestController waits for incoming web requests at specifically defined URLs (like "/students" or "/courses").
- **Processing Data:** When a request arrives, the controller can read information from that request, such as data for creating a new student or the ID of a course to update.
- **Communicating with Services:** Controllers don't do the heavy lifting themselves—they ask the Service layer to perform actions like saving, fetching, or deleting data. This keeps controllers simple and focused on input/output.
- **Sending Responses:** Once a service finishes an operation, the controller turns the result (often a DTO) into a response for the user, including status codes like 200 OK or 404 Not Found.

---

## Key Endpoints (With Everyday Explanations)

Below, you'll find breakdowns for the most important endpoints in each major controller (Student, Course, Teacher, Department, Review), drawn from the actual code, mapped to plain-English scenarios.

## StudentController Endpoints

- **Create a Student (`POST /students`)**
    - *Purpose:* Add a new student to the system.
    - *Example:* A university admin fills out a form to register a student for classes.
    - *What Happens:* The app reads the form details, validates them, and passes them to the service. The student is created, and the details are returned.
- **Get a Student (`GET /students/{studentId}`)**
    - *Purpose:* Retrieve a student's profile by their ID.
    - *Example:* Looking up a student to check their courses.
- **Get All Students (`GET /students`)**
    - *Purpose:* See a list of all students.
- **Update Student (`PUT /students/{studentId}`)**
    - *Purpose:* Change student info, like correcting an email address or updating enrollment status.
- **Delete Student (`DELETE /students/{studentId}`)**
    - *Purpose:* Remove a student from the system (for graduated or withdrawn students).
- **Enroll a Student in Course (`PUT /students/{studentId}/courses/{courseId}`)**
    - *Purpose:* Add a student to a new class.
- **Remove Student from Course (`PUT /students/{studentId}/courses/{courseId}/remove`)**
    - *Purpose:* Unenroll a student from a course.

## CourseController Endpoints

- **Create a Course (`POST /courses`)**
    - *Purpose:* Add a new course offering.
- **Get a Course (`GET /courses/{courseId}`)**
    - *Purpose:* Find course details by ID (description, credits, etc.).
- **Get All Courses (`GET /courses`)**
    - *Purpose:* List all available courses.
- **Update Course (`PUT /courses/{courseId}`)**
    - *Purpose:* Change course details (like updating the description or dates).
- **Delete Course (`DELETE /courses/{courseId}`)**
    - *Purpose:* Cancel or remove a course.
- **Get Students Enrolled (`GET /courses/{courseId}/students`)**
    - *Purpose:* See who is attending the course.
- **Get Assigned Teachers (`GET /courses/{courseId}/teachers`)**
    - *Purpose:* List teachers teaching the course.
- **Assign Teacher (`PUT /courses/{courseId}/teachers/{teacherId}`)**
    - *Purpose:* Add a teacher to a course.
- **Remove Teacher (`PUT /courses/{courseId}/teachers/{teacherId}/remove`)**
    - *Purpose:* Unassign a teacher from the course.

## TeacherController Endpoints

- **Create a Teacher (`POST /teachers`)**
    - *Purpose:* Add a new teacher to the system.
- **Get a Teacher (`GET /teachers/{teacherId}`)**
    - *Purpose:* View a teacher's information.
- **Get All Teachers (`GET /teachers`)**
    - *Purpose:* List all teachers.
- **Update Teacher (`PUT /teachers/{teacherId}`)**
    - *Purpose:* Correct teacher data (like contact details).
- **Delete Teacher (`DELETE /teachers/{teacherId}`)**
    - *Purpose:* Remove a teacher (for retirement or resignation).
- **Get Courses Taught (`GET /teachers/{teacherId}/courses`)**
    - *Purpose:* See the classes assigned to a teacher.
- **Assign Course (`PUT /teachers/{teacherId}/courses/{courseId}`)**
    - *Purpose:* Add a course for the teacher to teach.
- **Remove Course (`PUT /teachers/{teacherId}/courses/{courseId}/remove`)**
    - *Purpose:* Take away a teaching assignment.

## DepartmentController Endpoints

- **Create a Department (`POST /departments`)**
    - *Purpose:* Start a new department (like Mathematics).
- **Get Department (`GET /departments/{departmentId}`)**
    - *Purpose:* Check details for a specific department.
- **Get All Departments (`GET /departments`)**
    - *Purpose:* List every department.
- **Update Department (`PUT /departments/{departmentId}`)**
    - *Purpose:* Change department info (rename, update contacts).
- **Delete Department (`DELETE /departments/{departmentId}`)**
    - *Purpose:* Remove a department (for reorganization).
- **Get Courses in Department (`GET /departments/{departmentId}/courses`)**
    - *Purpose:* See all courses offered by the department.
- **Get Students in Department (`GET /departments/{departmentId}/students`)**
    - *Purpose:* View all students in the department.
- **Get Teachers in Department (`GET /departments/{departmentId}/teachers`)**
    - *Purpose:* List faculty assigned to the department.
- **Assign Teacher to Department (`PUT /departments/{departmentId}/teachers/{teacherId}`)**
    - *Purpose:* Connect a teacher to a department.
- **Assign Course to Department (`PUT /departments/{departmentId}/courses/{courseId}`)**
    - *Purpose:* Offer a course through the department.
- **Assign Student to Department (`PUT /departments/{departmentId}/students/{studentId}`)**
    - *Purpose:* Add a student to a department (for enrollment or transfer).

## ReviewController Endpoints

- **Create a Review (`POST /reviews`)**
    - *Purpose:* Submit feedback about a course, teacher, or student experience.
- **Get Review (`GET /reviews/{reviewId}`)**
    - *Purpose:* View a specific review.
- **Get Reviews by Course (`GET /reviews/course/{courseId}`)**
    - *Purpose:* Show all feedback linked to a course.
- **Get Reviews by Student (`GET /reviews/student/{studentId}`)**
    - *Purpose:* Track a student's submitted reviews.
- **Get Reviews by Teacher (`GET /reviews/teacher/{teacherId}`)**
    - *Purpose:* See feedback for a teacher.
- **Update Review (`PUT /reviews/{reviewId}`)**
    - *Purpose:* Edit a review (like correcting comments).
- **Delete Review (`DELETE /reviews/{reviewId}`)**
    - *Purpose:* Remove an inappropriate or outdated review.

---

## How Requests Are Handled Step-by-Step

1. **Receive Request:** The controller listens for HTTP requests at its defined URL.
2. **Validate Data:** Input is checked (e.g., required fields, valid formats).
3. **Call Service Layer:** The controller hands off processing to a corresponding service class, which manages business logic and database access.
4. **Get Results:** The service returns data (usually in DTO form), or confirms an operation succeeded.
5. **Build Response:** The controller wraps the outcome as a JSON response and provides a status code so the client knows what happened.

---

## Everyday Example

- Someone visits a website and uses a form to sign up as a student.
- The StudentController accepts the form data, checks it (is it an email? is the name present?), then asks the StudentService to save it in the system.
- The Service saves the info, and the Controller responds: “Student created!” with the details in JSON format.

---

## Summary

The RestController layer is designed to cleanly handle web requests, pass information to your Service layer, and neatly return useful results. The endpoints each perform a single, clear job—like registering a student, enrolling in a course, or collecting feedback—and keep the process safe and straightforward. By organizing controllers this way, you build trust with users and make your code simple to maintain.
