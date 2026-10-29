
package com.example.studentcrud.controller;

import com.example.studentcrud.dto.StudentRequest;
import com.example.studentcrud.dto.StudentResponse;
import com.example.studentcrud.entity.Student;
import com.example.studentcrud.service.StudentService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create a student and return the saved details.
    @PostMapping
    public ResponseEntity<StudentResponse> addStudent(
            @Valid @RequestBody StudentRequest request) {

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        student.setCourse(request.getCourse());

        Student savedStudent = studentService.addStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StudentResponse.fromEntity(savedStudent));
    }

    // Return all students using response DTOs.
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {

        List<StudentResponse> students = studentService.getAllStudents()
                .stream()
                .map(StudentResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(students);
    }

    // Return one student by ID.
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable Long id) {

        Student student = studentService.getStudentById(id);

        return ResponseEntity.ok(StudentResponse.fromEntity(student));
    }

    // Update an existing student.
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        student.setCourse(request.getCourse());

        Student updatedStudent = studentService.updateStudent(id, student);

        return ResponseEntity.ok(
                StudentResponse.fromEntity(updatedStudent));
    }

    // Delete a student and return HTTP 204.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}
