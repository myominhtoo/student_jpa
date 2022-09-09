package com.lionel.student_jpa.controller.rest;

import com.lionel.student_jpa.model.Course;
import com.lionel.student_jpa.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( value = "/api" )
public class CourseApi {

    @Autowired
    CourseService courseService;

    // get courses
    @GetMapping( value = "/courses" )
    public List<Course> getCourses(){
        return courseService.findAll();
    }

    @PostMapping( value = "/courses" )


}
