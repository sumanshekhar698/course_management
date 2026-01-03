package com.trumio.course_management.dao;

import com.trumio.course_management.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseDao extends JpaRepository<Course, Long> {

    // Custom query: Find all courses a specific student is enrolled in
    List<Course> findByStudents_Id(Integer studentId);

    // Custom query: Find courses by title containing a keyword
    List<Course> findByTitleContaining(String keyword);

    @Query("SELECT c FROM Course c WHERE size(c.students) >= :minStudents")
    List<Course> findPopularCourses(@Param("minStudents") int minStudents);
}