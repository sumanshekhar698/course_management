package com.trumio.course_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Setter
//@Getter
@Data
@Entity
@Table(name = "course")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String title;

    @Column(length = 255)
    private String description;

    @ManyToMany(mappedBy = "courses") // References the 'courses' field in Student
    @JsonIgnoreProperties("courses") // <--- Add this
    private List<Student> students = new ArrayList<>();


}
