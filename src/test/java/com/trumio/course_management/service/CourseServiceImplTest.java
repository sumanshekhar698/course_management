package com.trumio.course_management.service;

import com.trumio.course_management.dao.CourseDao;
import com.trumio.course_management.entities.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseDao courseDao;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
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
    void testSaveCourse() {
        when(courseDao.save(any(Course.class))).thenReturn(course1);//Mocking it

        Course savedCourse = courseService.saveCourse(course1);

        assertNotNull(savedCourse);
        assertEquals(1L, savedCourse.getId());
        assertEquals("Spring Boot", savedCourse.getTitle());
        verify(courseDao, times(1)).save(any(Course.class));
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = Arrays.asList(course1, course2);
        when(courseDao.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAllCourses();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Spring Boot", result.get(0).getTitle());
        assertEquals("Microservices", result.get(1).getTitle());
        verify(courseDao, times(1)).findAll();
    }

    @Test
    void testGetCourseById() {
        when(courseDao.findById(anyLong())).thenReturn(Optional.of(course1));

        Course result = courseService.getCourseById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Spring Boot", result.getTitle());
        verify(courseDao, times(1)).findById(1L);
    }

    @Test
    void testGetCourseById_NotFound() {
        when(courseDao.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            courseService.getCourseById(1L);
        });

        assertEquals("Course not found", exception.getMessage());
        verify(courseDao, times(1)).findById(1L);
    }

    @Test
    void testGetPopularCourses() {
        List<Course> popularCourses = Arrays.asList(course1);
        when(courseDao.findPopularCourses(anyInt())).thenReturn(popularCourses);

        List<Course> result = courseService.getPopularCourses(5);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Spring Boot", result.get(0).getTitle());
        verify(courseDao, times(1)).findPopularCourses(5);
    }

    @Test
    void testGetPopularCourses_EmptyList() {
        when(courseDao.findPopularCourses(anyInt())).thenReturn(Arrays.asList());

        List<Course> result = courseService.getPopularCourses(10);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(courseDao, times(1)).findPopularCourses(10);
    }
}
