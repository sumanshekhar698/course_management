package com.trumio.course_management.controller;

import com.trumio.course_management.entities.Course;
import com.trumio.course_management.service.CourseService;
import com.trumio.course_management.service.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Course> getAllCourses() {

        List<Course> allCourses = courseService.getAllCourses();
        logger.info("Fetched :: {} courses.", allCourses.size());
        return allCourses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/popular")
    public List<Course> getPopularCourses(@RequestParam(defaultValue = "1") int min) {
        return courseService.getPopularCourses(min);
    }
}