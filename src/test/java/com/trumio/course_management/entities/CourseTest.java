package com.trumio.course_management.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    private Course course;
    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        course = new Course();

        student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");

        student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Smith");
    }

    @Test
    void testCourseCreation() {
        assertNotNull(course);
        assertNotNull(course.getStudents());
        assertTrue(course.getStudents().isEmpty());
    }

    @Test
    void testSetAndGetId() {
        course.setId(1L);
        assertEquals(1L, course.getId());
    }

    @Test
    void testSetAndGetTitle() {
        course.setTitle("Spring Boot");
        assertEquals("Spring Boot", course.getTitle());
    }

    @Test
    void testSetAndGetDescription() {
        course.setDescription("Learn Spring Boot framework");
        assertEquals("Learn Spring Boot framework", course.getDescription());
    }

    @Test
    void testSetAndGetStudents() {
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        course.setStudents(students);

        assertEquals(2, course.getStudents().size());
        assertTrue(course.getStudents().contains(student1));
        assertTrue(course.getStudents().contains(student2));
    }

    @Test
    void testAddStudentToCourse() {
        course.getStudents().add(student1);

        assertEquals(1, course.getStudents().size());
        assertEquals("John Doe", course.getStudents().get(0).getName());
    }

    @Test
    void testRemoveStudentFromCourse() {
        course.getStudents().add(student1);
        course.getStudents().add(student2);

        assertEquals(2, course.getStudents().size());

        course.getStudents().remove(student1);

        assertEquals(1, course.getStudents().size());
        assertFalse(course.getStudents().contains(student1));
        assertTrue(course.getStudents().contains(student2));
    }

    @Test
    void testCourseEquality() {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Spring Boot");
        course1.setDescription("Learn Spring Boot");

        Course course2 = new Course();
        course2.setId(1L);
        course2.setTitle("Spring Boot");
        course2.setDescription("Learn Spring Boot");

        assertEquals(course1, course2);
        assertEquals(course1.hashCode(), course2.hashCode());
    }

    @Test
    void testCourseInequality() {
        Course course1 = new Course();
        course1.setId(1L);
        course1.setTitle("Spring Boot");

        Course course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Microservices");

        assertNotEquals(course1, course2);
    }

    @Test
    void testToString() {
        course.setId(1L);
        course.setTitle("Spring Boot");
        course.setDescription("Learn Spring Boot");

        String toString = course.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("Spring Boot"));
    }

    @Test
    void testCourseWithNullValues() {
        Course emptyCourse = new Course();

        assertNull(emptyCourse.getId());
        assertNull(emptyCourse.getTitle());
        assertNull(emptyCourse.getDescription());
        assertNotNull(emptyCourse.getStudents());
    }
}
