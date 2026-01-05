package com.trumio.course_management.service;

import com.trumio.course_management.dao.CourseDao;
import com.trumio.course_management.dao.StudentDao;
import com.trumio.course_management.entities.Course;
import com.trumio.course_management.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentDao studentDao;

    @Mock
    private CourseDao courseDao;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student1;
    private Student student2;
    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");
        student1.setCity("New York");
        student1.setVersion(0);
        student1.setCourses(new ArrayList<>());

        student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Smith");
        student2.setCity("London");
        student2.setVersion(0);
        student2.setCourses(new ArrayList<>());

        course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Spring Boot");
        course1.setDescription("Learn Spring Boot");

        course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Microservices");
        course2.setDescription("Learn Microservices Architecture");
    }

    @Test
    void testRegisterStudent() {
        when(studentDao.save(any(Student.class))).thenReturn(student1);

        Student savedStudent = studentService.registerStudent(student1);

        assertNotNull(savedStudent);
        assertEquals(1L, savedStudent.getId());
        assertEquals("John Doe", savedStudent.getName());
        assertEquals("New York", savedStudent.getCity());
        verify(studentDao, times(1)).save(any(Student.class));
    }

    @Test
    void testEnrollStudentInCourse() {
        when(studentDao.findById(anyLong())).thenReturn(Optional.of(student1));
        when(courseDao.findById(anyLong())).thenReturn(Optional.of(course1));

        studentService.enrollStudentInCourse(1L, 1L);

        assertTrue(student1.getCourses().contains(course1));
        verify(studentDao, times(1)).findById(1L);
        verify(courseDao, times(1)).findById(1L);
    }

    @Test
    void testEnrollStudentInCourse_StudentNotFound() {
        when(studentDao.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.enrollStudentInCourse(1L, 1L);
        });

        assertEquals("Student not found", exception.getMessage());
        verify(studentDao, times(1)).findById(1L);
        verify(courseDao, times(0)).findById(anyLong());
    }

    @Test
    void testEnrollStudentInCourse_CourseNotFound() {
        when(studentDao.findById(anyLong())).thenReturn(Optional.of(student1));
        when(courseDao.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.enrollStudentInCourse(1L, 1L);
        });

        assertEquals("Course not found", exception.getMessage());
        verify(studentDao, times(1)).findById(1L);
        verify(courseDao, times(1)).findById(1L);
    }

    @Test
    void testEnrollStudentInMultipleCourses() {
        when(studentDao.findById(anyLong())).thenReturn(Optional.of(student1));
        when(courseDao.findById(1L)).thenReturn(Optional.of(course1));
        when(courseDao.findById(2L)).thenReturn(Optional.of(course2));

        studentService.enrollStudentInCourse(1L, 1L);
        studentService.enrollStudentInCourse(1L, 2L);

        assertEquals(2, student1.getCourses().size());
        assertTrue(student1.getCourses().contains(course1));
        assertTrue(student1.getCourses().contains(course2));
    }

    @Test
    void testGetStudentsByCourse() {
        List<Student> students = Arrays.asList(student1, student2);
        when(studentDao.findByCourses_Title(anyString())).thenReturn(students);

        List<Student> result = studentService.getStudentsByCourse("Spring Boot");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
        verify(studentDao, times(1)).findByCourses_Title("Spring Boot");
    }

    @Test
    void testGetStudentsByCourse_EmptyList() {
        when(studentDao.findByCourses_Title(anyString())).thenReturn(new ArrayList<>());

        List<Student> result = studentService.getStudentsByCourse("NonExistent Course");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(studentDao, times(1)).findByCourses_Title("NonExistent Course");
    }
}
