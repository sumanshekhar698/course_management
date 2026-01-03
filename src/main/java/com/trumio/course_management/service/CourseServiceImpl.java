package com.trumio.course_management.service;

import com.trumio.course_management.dao.CourseDao;
import com.trumio.course_management.entities.Course;
import com.trumio.course_management.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public Course saveCourse(Course course) {
        return courseDao.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDao.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseDao.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }
    @Override
    public List<Course> getPopularCourses(int minStudents) {
        return courseDao.findPopularCourses(minStudents);
    }

}