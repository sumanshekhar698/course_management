package com.trumio.course_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trumio.course_management.entities.Student;
import com.trumio.course_management.service.StudentService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc


//@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");
        student1.setCity("New York");
        student1.setVersion(0);

        student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Smith");
        student2.setCity("London");
        student2.setVersion(0);
    }

    @Test
    @WithMockUser(username = "teacher", roles = {"ADMIN"})
    void testRegisterStudent() throws Exception {
        when(studentService.registerStudent(any(Student.class))).thenReturn(student1);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.city").value("New York"));
    }

    @Test
    @WithMockUser(username = "teacher", roles = {"ADMIN"})
    void testEnrollInCourse() throws Exception {
        doNothing().when(studentService).enrollStudentInCourse(anyLong(), anyLong());

        mockMvc.perform(put("/api/students/1/enroll/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student enrolled successfully!"));
    }

    @Test
    @WithMockUser(username = "teacher", roles = {"ADMIN"})
    void testGetStudentsByCourse() throws Exception {
        List<Student> students = Arrays.asList(student1, student2);
        when(studentService.getStudentsByCourse(anyString())).thenReturn(students);

        mockMvc.perform(get("/api/students/course/Spring Boot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }
}
