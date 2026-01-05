package com.trumio.course_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trumio.course_management.entities.Course;
import com.trumio.course_management.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@WithMockUser
//@WebMvcTest(CourseController.class)

@SpringBootTest
@AutoConfigureMockMvc // Needed to simulate API calls
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
    @MockitoBean
    private CourseService courseService;//Mock It

    @Autowired
    private ObjectMapper objectMapper;

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
    @WithMockUser(username = "teacher", roles = {"ADMIN"})
    void testCreateCourse() throws Exception {
        when(courseService.saveCourse(any(Course.class))).thenReturn(course1);//Mocking

        mockMvc.perform(post("/api/courses")//Testing it
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Spring Boot"))
                .andExpect(jsonPath("$.description").value("Learn Spring Boot"));
    }

    @Test
    @WithMockUser(username = "teacher", roles = {"ADMIN"})
    void testGetAllCourses() throws Exception {
        List<Course> courses = Arrays.asList(course1, course2);
        when(courseService.getAllCourses()).thenReturn(courses);//Mocking it

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Spring Boot"))
                .andExpect(jsonPath("$[1].title").value("Microservices"));
    }

    @Test
    @WithMockUser(username = "teacher", roles = {"ADMIN"})
    void testGetCourseById() throws Exception {
        when(courseService.getCourseById(anyLong())).thenReturn(course1);

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Spring Boot"));
    }

    @Test
    void testGetPopularCourses() throws Exception {
        List<Course> popularCourses = Arrays.asList(course1);
        when(courseService.getPopularCourses(anyInt())).thenReturn(popularCourses);

        mockMvc.perform(get("/api/courses/popular")
                        .param("min", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Spring Boot"));
    }

    @Test
    void testGetPopularCoursesWithDefaultMinValue() throws Exception {
        List<Course> popularCourses = Arrays.asList(course1, course2);
        when(courseService.getPopularCourses(1)).thenReturn(popularCourses);

        mockMvc.perform(get("/api/courses/popular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
