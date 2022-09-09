package com.lionel.student_jpa.controller.rest;

import com.lionel.student_jpa.model.HttpResponse;
import com.lionel.student_jpa.model.User;
import com.lionel.student_jpa.service.UserService;
import com.lionel.student_jpa.utils.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin( origins = "http://localhost:3000" )
@RequestMapping( value = "/api" )
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping( value = "/users" )
    public List<User> getUsers(){
        return userService.findAll();
    }

    @PostMapping( value = "/users" )
    public HttpResponse postCreateUser( @RequestBody User user ){
        HttpResponse httpResponse = new HttpResponse();

        if( userService.isEmailDuplicate(user.getEmail()) ){
            httpResponse.setStatusCode( 200 );
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
            httpResponse.setOk( false );
            httpResponse.setMsg( "Duplicate Email!" );
            return httpResponse;
        }

        user.setId(Generator.generateId(userService.getMaxid(),"USR"));
        if( userService.save( user ) ){
            httpResponse.setStatusCode( 200 );
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
            httpResponse.setOk( true );
            httpResponse.setMsg( "Successfully Created!" );
            return httpResponse;
        }else{
            httpResponse.setStatusCode( 200 );
            httpResponse.setHttpStatus( HttpStatus.BAD_REQUEST );
            httpResponse.setOk( false );
            httpResponse.setMsg( "There was error in creating user!" );
            return httpResponse;
        }

//        return httpResponse;
    }

    @GetMapping( value = "/users/{id}" )
    public User getUser( @PathVariable("id") String userId ){
        return userService.findById(userId);
    }

    @DeleteMapping( value = "/users/{id}" )
    public HttpResponse deleteUser( @PathVariable("id") String userId ){
        HttpResponse httpResponse = new HttpResponse();

        if( userService.deleteOne(userId) ){
            httpResponse.setStatusCode( 200 );
            httpResponse.setOk( true );
            httpResponse.setMsg("Successfully Deleted!");
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
        }else{
            httpResponse.setStatusCode( 200 );
            httpResponse.setOk( false );
            httpResponse.setMsg("There was error in deleting user!");
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
        }

        return httpResponse;
    }

    @PutMapping( value = "/users/{id}" )
    public HttpResponse updateUser( @PathVariable("id") String userId  , @RequestBody User user ){
        HttpResponse httpResponse = new HttpResponse();

        User savedUser = userService.findByEmail( user.getEmail() );

        if( savedUser != null && !savedUser.getId().equals(user.getId())){
            httpResponse.setHttpStatus( HttpStatus.ALREADY_REPORTED );
            httpResponse.setMsg("Duplicate email!");
            httpResponse.setOk( false );
            httpResponse.setStatusCode( 200 );
            return httpResponse;
        }

        if( userService.updateOne(user) ){
            httpResponse.setHttpStatus( HttpStatus.ACCEPTED );
            httpResponse.setMsg("Successfully Updated!");
            httpResponse.setOk( true );
            httpResponse.setStatusCode( 200 );
            return httpResponse;
        }else{
            httpResponse.setHttpStatus( HttpStatus.BAD_REQUEST );
            httpResponse.setMsg("There was error in updating user!");
            httpResponse.setOk( false );
            httpResponse.setStatusCode( 200 );
        }

        return httpResponse;
    }

}
