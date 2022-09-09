package com.lionel.student_jpa.controller.rest;

import com.lionel.student_jpa.model.HttpResponse;
import com.lionel.student_jpa.model.Student;
import com.lionel.student_jpa.service.StudentService;
import com.lionel.student_jpa.utils.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/api" )
public class StudentApi {

    @Autowired
    private StudentService studentService;

    @GetMapping( value = "/students" )
    public List<Student> getStudents(){
        return studentService.findAll();
    }

    @PostMapping( value = "/students" )
    public HttpResponse postCreateStudent( @RequestBody Student student ){
        HttpResponse  httpResponse = new HttpResponse();

        student.setId( Generator.generateId( studentService.getMaxId() , "STU" ) );

        if( studentService.save( student )){
            httpResponse.setHttpStatus(HttpStatus.CREATED);
            httpResponse.setMsg("Successfully Created!");
            httpResponse.setOk( true );
            httpResponse.setStatusCode( 200 );
            return httpResponse;
        }else{
            httpResponse.setHttpStatus(HttpStatus.ACCEPTED);
            httpResponse.setMsg("There was error in creating student!");
            httpResponse.setOk( false );
            httpResponse.setStatusCode( 200 );
        }

        return httpResponse;
    }

    @GetMapping( value = "/students/{id}" )
    public Student getStudent( @PathVariable("id") String studentId ){
        return studentService.findById( studentId );
    }

    @DeleteMapping( value = "/students/{id}" )
    public HttpResponse deleteStudent( @PathVariable("id") String studentId ){
        HttpResponse httpResponse = new HttpResponse();

        if( studentService.deleteOne(studentId) ){
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
            httpResponse.setMsg("Successfully Deleted!");
            httpResponse.setOk( true );
            httpResponse.setStatusCode( 200 );

            return httpResponse;
        }else{
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
            httpResponse.setMsg("There was error in deleting student!");
            httpResponse.setOk( false );
            httpResponse.setStatusCode( 200 );

            return httpResponse;
        }
    }

    @PutMapping( value = "/students/{id}" )
    public HttpResponse updateStudent( @RequestBody Student student ){
        HttpResponse httpResponse = new HttpResponse();

        if( studentService.updateOne( student )){
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
            httpResponse.setOk( true );
            httpResponse.setMsg( "Successfully Updated!" );
            httpResponse.setStatusCode( 200 );

            return httpResponse;
        }else{
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
            httpResponse.setOk( false );
            httpResponse.setMsg( "There was error in updating student!" );
            httpResponse.setStatusCode( 200 );
        }
        return httpResponse;
    }


}
