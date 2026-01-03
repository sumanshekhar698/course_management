package com.trumio.course_management.dao;

import com.trumio.course_management.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {
    
    // Custom query: Find all students enrolled in a specific course by its title
    List<Student> findByCourses_Title(String title);
    
    // Custom query: Find students by city
    List<Student> findByCity(String city);
}