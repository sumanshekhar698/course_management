package com.trumio.course_management.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;
    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        student = new Student();

        course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Spring Boot");

        course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Microservices");
    }

    @Test
    void testStudentCreation() {
        assertNotNull(student);
        assertNotNull(student.getCourses());
        assertTrue(student.getCourses().isEmpty());
    }


    @Test
    void testSetAndGetId() {
        student.setId(1L);
        assertEquals(1L, student.getId());
    }

    @Test
    void testSetAndGetName() {
        student.setName("John Doe");
        assertEquals("John Doe", student.getName());
    }

    @Test
    void testSetAndGetCity() {
        student.setCity("New York");
        assertEquals("New York", student.getCity());
    }

    @Test
    void testSetAndGetVersion() {
        student.setVersion(0);
        assertEquals(0, student.getVersion());

        student.setVersion(1);
        assertEquals(1, student.getVersion());
    }

    @Test
    void testSetAndGetCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        student.setCourses(courses);

        assertEquals(2, student.getCourses().size());
        assertTrue(student.getCourses().contains(course1));
        assertTrue(student.getCourses().contains(course2));
    }

    @Test
    void testAddCourseToStudent() {
        student.getCourses().add(course1);

        assertEquals(1, student.getCourses().size());
        assertEquals("Spring Boot", student.getCourses().get(0).getTitle());
    }

    @Test
    void testAddMultipleCoursesToStudent() {
        student.getCourses().add(course1);
        student.getCourses().add(course2);

        assertEquals(2, student.getCourses().size());
        assertTrue(student.getCourses().contains(course1));
        assertTrue(student.getCourses().contains(course2));
    }

    @Test
    void testRemoveCourseFromStudent() {
        student.getCourses().add(course1);
        student.getCourses().add(course2);

        assertEquals(2, student.getCourses().size());

        student.getCourses().remove(course1);

        assertEquals(1, student.getCourses().size());
        assertFalse(student.getCourses().contains(course1));
        assertTrue(student.getCourses().contains(course2));
    }

    @Test
    void testStudentEquality() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");
        student1.setCity("New York");
        student1.setVersion(0);

        Student student2 = new Student();
        student2.setId(1L);
        student2.setName("John Doe");
        student2.setCity("New York");
        student2.setVersion(0);

        assertEquals(student1, student2);
        assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    void testStudentInequality() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Smith");

        assertNotEquals(student1, student2);
    }

    @Test
    void testToString() {
        student.setId(1L);
        student.setName("John Doe");
        student.setCity("New York");
        student.setVersion(0);

        String toString = student.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("John Doe"));
        assertTrue(toString.contains("New York"));
    }

    @Test
    void testStudentWithNullValues() {
        Student emptyStudent = new Student();

        assertNull(emptyStudent.getId());
        assertNull(emptyStudent.getName());
        assertNull(emptyStudent.getCity());
        assertNull(emptyStudent.getVersion());
        assertNotNull(emptyStudent.getCourses());
    }

    @Test
    void testVersionIncrement() {
        student.setVersion(0);
        assertEquals(0, student.getVersion());

        // Simulate version increment (typically done by JPA)
        student.setVersion(student.getVersion() + 1);
        assertEquals(1, student.getVersion());
    }
}
