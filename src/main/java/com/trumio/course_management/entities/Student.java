package com.trumio.course_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 200)
    private String city;

    @Version // Automatically handles the 'version' column for concurrency
    @Column(nullable = false)
    private Integer version;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("students") // <--- Add this
    @JoinTable(
            name = "student_course", // Name of the join table
            joinColumns = @JoinColumn(name = "student_id"), // Foreign key for Student
            inverseJoinColumns = @JoinColumn(name = "course_id") // Foreign key for Course
    )
    private List<Course> courses = new ArrayList<>();
}