-- Drop tables if they exist to start fresh (Optional for development)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS student_course;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS course;
SET FOREIGN_KEY_CHECKS = 1;

-- Create Course Table
CREATE TABLE course (
                        id BIGINT NOT NULL,
                        description VARCHAR(255) DEFAULT NULL,
                        title VARCHAR(255) DEFAULT NULL,
                        PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Create Student Table
CREATE TABLE student (
                         id BIGINT NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         city VARCHAR(200) DEFAULT NULL,
                         version INT NOT NULL,
                         PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Create Join Table for Many-to-Many Relationship
CREATE TABLE student_course (
                                student_id BIGINT NOT NULL,
                                course_id BIGINT NOT NULL,
                                PRIMARY KEY (student_id, course_id),
                                CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES student (id),
                                CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES course (id)
) ENGINE=InnoDB;