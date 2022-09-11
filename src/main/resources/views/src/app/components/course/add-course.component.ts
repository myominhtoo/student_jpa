import { Component } from '@angular/core';
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


@Component({
    selector : 'add-course',
    templateUrl : './add-course.component.html',
})
export class AddCourseComponent {

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

}