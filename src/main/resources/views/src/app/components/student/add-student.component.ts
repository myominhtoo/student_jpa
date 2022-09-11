import { Component , OnInit  } from '@angular/core';
import { Course } from 'src/app/models/Course';
import { Status } from 'src/app/models/Status';
import { Student } from 'src/app/models/Student';
import CourseService from 'src/app/services/course/CourseService';
import StudentService from 'src/app/services/student/StudentService';
import { fetchCourses } from 'src/app/util/courses';
import handleAddCourse from 'src/app/util/handleAddCourse';
import { NgForm } from '@angular/forms';
import useValidator from 'src/app/util/validator/useValidator';
import { studentValidate } from 'src/app/util/validator/validators';
import StudentError from 'src/app/models/error/StudentError';
import swal from 'sweetalert';
import { Router } from '@angular/router';
import isAllOk from 'src/app/util/validator/isAllOk';
import resetAllError from 'src/app/util/resetErrors';

@Component({
    selector : 'add-student',
    templateUrl : './add-student.component.html',
})
export class AddStudentComponent implements OnInit {

    constructor(
         private studentService : StudentService,
         private coureService : CourseService,
         private router : Router
    ){}

    student : Student = {
        id : '',
        name : '',
        dob : '',
        gender : 0,
        education : 0,
        phone : '',
        attendCourses : [],
    }

    status : Status = {
        isBlank : false,
        isLoading : false,
    }

    isGettingId : boolean = false;

   data : {
        courses : Course[]
   } = {
        courses : [],
   }

   error = StudentError;

    ngOnInit() : void {
        this.getStudentId();
        fetchCourses( this.coureService , { data : this.data , status : this.status } );// getting courses required for student 

    }

    addAttendCourse( target : string ) : void {
        handleAddCourse( { attendCourses : this.student.attendCourses , target : target} );
    }

    // will get student id from api when inited this component
    getStudentId(){
        this.isGettingId = true;
        this.studentService.getId()
        .subscribe({
            next : ( res ) => {
                this.isGettingId = false;
                
                if( res.ok ){
                    this.student.id = res.msg ;
                }else{
                    swal({
                        text : `Sorry , this site can't be reacted!`,
                        icon : 'warning',
                        dangerMode : true
                    }).then( () => {
                        this.router.navigate(['/students']);
                    })

                }
            },
            error : ( e ) => console.log( e )
        })
    }


    handleAddStudent( form : NgForm ) : void {
       let keys = Object.keys( this.student );
       let values = Object.values( this.student );

       useValidator( studentValidate , keys , values  );


       if( form.valid && isAllOk('student') ){
            this.studentService.addStudent( this.student )
            .subscribe({
                next : ( res ) => {

                    if( res.ok ){

                        swal({
                            text : res.msg,
                            icon : 'success'
                        }).then( () => {
                            this.router.navigate(['/students']);
                        })

                    }else{

                    }

                },
                error : ( e ) => console.log( e )
            });
       }

    }

    ngOnDestroy() : void {
        resetAllError('student');
    }

}