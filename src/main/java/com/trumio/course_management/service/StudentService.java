package com.trumio.course_management.service;

import com.trumio.course_management.entities.Student;
import java.util.List;

public interface StudentService {
    Student registerStudent(Student student);
    void enrollStudentInCourse(Long studentId, Long courseId);
    List<Student> getStudentsByCourse(String courseTitle);
}