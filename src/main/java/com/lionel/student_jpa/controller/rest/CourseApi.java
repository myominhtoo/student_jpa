package com.lionel.student_jpa.controller.rest;

import com.lionel.student_jpa.model.Course;
import com.lionel.student_jpa.model.HttpResponse;
import com.lionel.student_jpa.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    //creating course
    @PostMapping( value = "/courses" )
    public HttpResponse postCreateCourse( @RequestBody Course course ){
        HttpResponse httpResponse = new HttpResponse();

        if( courseService.isDuplicate(course.getId()) ){
            httpResponse.setHttpStatus(HttpStatus.ALREADY_REPORTED );
            httpResponse.setStatusCode( 200 );
            httpResponse.setOk( false );
            httpResponse.setMsg("Course Id must be unique!");
            return httpResponse;
        }

        if( !courseService.save(course) ){
            httpResponse.setHttpStatus( HttpStatus.BAD_REQUEST );
            httpResponse.setStatusCode( 200 );
            httpResponse.setOk( false );
            httpResponse.setMsg("There was error in creating course!");
            return httpResponse;
        }

        httpResponse.setHttpStatus( HttpStatus.CREATED );
        httpResponse.setStatusCode( 200 );
        httpResponse.setOk( true );
        httpResponse.setMsg("Successfully Created!");

        return httpResponse;
    }

    // getting course
    @GetMapping( value = "/courses/{id}")
    public Course getCourse( @PathVariable("id") String courseId ){
        return courseService.findById( courseId );
    }

    // delete course
    @DeleteMapping( value = "/courses/{id}" )
    public HttpResponse deleteCourse( @PathVariable("id") String courseId ){
        HttpResponse httpResponse = new HttpResponse();

        if( !courseService.deleteOne( courseId ) ){
            httpResponse.setHttpStatus( HttpStatus.BAD_REQUEST );
            httpResponse.setMsg("There was error in deleting!");
            httpResponse.setOk( false );
            httpResponse.setStatusCode( 200 );
        }else{
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
            httpResponse.setMsg("Successfully Deleted!");
            httpResponse.setOk( true );
            httpResponse.setStatusCode( 200 );
        }

        return httpResponse;
    }

    @PutMapping( value = "/courses/{id}" )
    public HttpResponse updateCourse( @PathVariable("id") String courseId , @RequestBody Course course ){
        HttpResponse httpResponse = new HttpResponse();

        if( courseService.updateOne( course )){
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
            httpResponse.setMsg("Successfully Updated!");
            httpResponse.setOk( true );
            httpResponse.setStatusCode( 200 );
        }else{
            httpResponse.setHttpStatus( HttpStatus.BAD_REQUEST );
            httpResponse.setMsg("There was error in updating course!");
            httpResponse.setOk( false );
            httpResponse.setStatusCode( 200 );
        }

        return httpResponse;
    }

}
