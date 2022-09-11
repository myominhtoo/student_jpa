package com.lionel.student_jpa.controller.rest;

import com.lionel.student_jpa.model.HttpResponse;
import com.lionel.student_jpa.model.Student;
import com.lionel.student_jpa.service.StudentService;
import com.lionel.student_jpa.utils.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin( origins = "http://localhost:3000" )
@RequestMapping( value = "/api" )
public class StudentApi {

    @Autowired
    private StudentService studentService;

    @GetMapping( value = "/students" )
    public List<Student> getStudents( 
        @RequestParam( value = "id" , required = false ) String id ,
        @RequestParam( value = "name" , required =  false ) String name ,
        @RequestParam( value = "course" , required = false ) String course ){

        List<Student> students = new ArrayList<>();

        if( id == null && name == null && course == null ){
            students = studentService.findAll();
            return students;
        }

        if( id != null && name == null && course == null ){
            students = studentService.findWithId("%"+id+"%");
            return students;
        }
        else if( id == null && name != null  && course == null ){
            students = studentService.findWithName("%"+name+"%");
            return students;
        }else if( id == null && name == null && course != null ){
            students = studentService.findWithCourse("%"+course+"%");
            return students;
        }else if( id != null  && name != null && course == null  ){
            students = studentService.findwithIdAndName("%"+id+"%", "%"+name+"%");
            return students;
        }else if( id != null && name == null && course != null ){
            students = studentService.findWithIdAndCourse("%"+id+"%", "%"+course+"%");
            return students;
        }else if( id == null && name != null && course != null  ){
            students = studentService.findWithNameAndCourse("%"+name+"%", "%"+course+"%");
            return students;
        }else{
            students = studentService.findWithIdAndNameAndCourse("%"+id+"%", "%"+name+"%", "%"+course+"%");
            return students;
        }

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

    // getting id for student
    @GetMapping( value = "/students/id" )
    public HttpResponse getStudentId(){
        HttpResponse httpResponse = new HttpResponse();

        httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
        httpResponse.setMsg( Generator.generateId( studentService.getMaxId() , "STU" ) );
        httpResponse.setOk( true );
        httpResponse.setStatusCode( 200 );

        return httpResponse;
    }

}
