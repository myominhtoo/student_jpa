import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Course } from 'src/app/models/Course';
import { courseValidate } from 'src/app/util/validator/validators';
import CourseError from 'src/app/models/error/CourseError';
import CourseService from 'src/app/services/course/CourseService';
import { Status } from 'src/app/models/Status';
import { Response } from 'src/app/models/Response';
import swal from 'sweetalert';
import { Router } from '@angular/router';
import isAllOk from 'src/app/util/validator/isAllOk';
import resetAllError from 'src/app/util/resetErrors';
import checkAuth from 'src/app/util/checkAuth';


@Component({
    selector : 'add-course',
    templateUrl : './add-course.component.html',
})
export class AddCourseComponent implements OnInit {

    course : Course = {
        id : '',
        name : ''
    };

    status : Status = {
        isLoading : false,
        isBlank : false
    }

    courseError = CourseError;

    constructor( private courseService : CourseService , private router : Router ){}

    ngOnInit(): void {
        if( !checkAuth() ){
            this.router.navigate( ['/login'] , {
                queryParams : {
                    msg : 'Please login to continue!',
                }  
            } );
        }
    }

    handleAddCourse( form : NgForm ) : void { 

        courseValidate( this.course.id , 'id' );
        courseValidate( this.course.name , 'name' );

        if( form.valid && isAllOk('course') ){

            this.status.isLoading = true;

            this.courseService.addCourse( this.course )
            .subscribe({
                next : ( res : Response ) => {          
                    this.status.isLoading = false;

                    if( !res.ok ){
                        this.courseError.id = { hasError : true , msg : res.msg };
                    }else{

                        swal({
                            text : res.msg,
                            icon : 'success'
                        }).then( () => {
                            this.router.navigate(['/courses']);
                        });

                    }   
                },
                error : ( e ) => console.log( e )
            })
        }

    }

    ngOnDestroy() : void {
        resetAllError('course');
    }

}