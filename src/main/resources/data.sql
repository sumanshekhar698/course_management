-- Insert Sample Courses
INSERT INTO course (id, title, description) VALUES (1, 'Spring Boot Fundamentals', 'Master the basics of Spring Framework 3.x');
INSERT INTO course (id, title, description) VALUES (2, 'MySQL for Beginners', 'Learn relational database design and SQL queries');
INSERT INTO course (id, title, description) VALUES (3, 'Microservices Architecture', 'Building scalable distributed systems');

-- Insert Sample Students
INSERT INTO student (id, name, city, version) VALUES (1, 'Alice Johnson', 'New York', 0);
INSERT INTO student (id, name, city, version) VALUES (2, 'Bob Smith', 'London', 0);
INSERT INTO student (id, name, city, version) VALUES (3, 'Charlie Brown', 'Berlin', 0);

-- Pre-enroll Alice in Spring Boot
INSERT INTO student_course (student_id, course_id) VALUES (1, 1);