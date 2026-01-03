package com.trumio.course_management.service;

import com.trumio.course_management.entities.Course;
import java.util.List;

public interface CourseService {
    Course saveCourse(Course course);
    List<Course> getAllCourses();
    Course getCourseById(Long id);
    List<Course> getPopularCourses(int minStudents);
}