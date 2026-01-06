package com.trumio.course_management.service;

import com.trumio.course_management.dao.CourseDao;
import com.trumio.course_management.dao.StudentDao;
import com.trumio.course_management.entities.Course;
import com.trumio.course_management.entities.Student;
import com.trumio.course_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseDao courseDao;

    @Override
    public Student registerStudent(Student student) {
        return studentDao.save(student);
    }

    @Override
    @Transactional // Ensures the whole operation succeeds or fails as a single unit
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentDao.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseDao.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Logic: Add course to student's list
        student.getCourses().add(course);
        
        // Since we are using @Transactional and JPA, 
        // changes are automatically flushed to the DB!
    }

    @Override
    public List<Student> getStudentsByCourse(String courseTitle) {
        return studentDao.findByCourses_Title(courseTitle);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }
}