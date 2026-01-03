//package com.trumio.course_management.controller;
//
//
//
//import com.trumio.course_management.entities.Course;
//import com.trumio.course_management.service.CourseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//public class CourseControllerOld {
//
//    @Autowired
//    private CourseService courseService;
//
//    @RequestMapping(value = "/course/test", method = RequestMethod.GET)
//    public String test() {
//        return "Testing connection ...";
//    }
//
//
//    @GetMapping(value = "/course/allcourses")
//    public List<Course> getCourses() {
//        return this.courseService.getCourses();
//    }
//
//
//    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.GET)
//    public Course getCourse(@PathVariable String courseId) {
//        return this.courseService.getCourse(Long.parseLong(courseId));
//    }
//
//
//    @PostMapping(path = "/courses")
//    public Course addCourse(@RequestBody Course course) {
//        return this.courseService.addCourse(course);
//    }
//
//
//    @PutMapping(path = "/courses")
//    public Course updateCourse(@RequestBody Course course) {
//        return this.courseService.updateCourse(course);
//    }
//
//    @DeleteMapping("courses/{courseId}")
//    ResponseEntity<HttpStatus> deleteCourse(@PathVariable String courseId) {
//
//        try {
//            this.courseService.deleteCourse(Long.parseLong(courseId));
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//}
