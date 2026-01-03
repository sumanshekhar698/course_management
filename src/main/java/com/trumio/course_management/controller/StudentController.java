package com.trumio.course_management.controller;

import com.trumio.course_management.entities.Student;
import com.trumio.course_management.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.registerStudent(student), HttpStatus.CREATED);
    }

    // This is the Many-to-Many enrollment endpoint
    @PutMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<String> enrollInCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        studentService.enrollStudentInCourse(studentId, courseId);
        return ResponseEntity.ok("Student enrolled successfully!");
    }

    @GetMapping("/course/{title}")
    public List<Student> getStudentsByCourse(@PathVariable String title) {
        List<Student> studentsByCourse = studentService.getStudentsByCourse(title);
        log.info("Fetched {} Students for course {}", studentsByCourse.size(), title);
        return studentsByCourse;
    }
}