import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from 'src/app/models/Course';
import StudentError from 'src/app/models/error/StudentError';
import { Status } from 'src/app/models/Status';
import { Student } from 'src/app/models/Student';
import CourseService from 'src/app/services/course/CourseService';
import StudentService from 'src/app/services/student/StudentService';
import { fetchCourses } from 'src/app/util/courses';
import handleAddCourse from 'src/app/util/handleAddCourse';
import resetAllError from 'src/app/util/resetErrors';
import isAllOk from 'src/app/util/validator/isAllOk';
import useValidator from 'src/app/util/validator/useValidator';
import { studentValidate } from 'src/app/util/validator/validators';
import swal from 'sweetalert';

@Component({
    selector : 'student-detail',
    templateUrl : './student-detail.component.html',
})
export class StudentDetailComponent implements OnInit{

    constructor(
        private studentService : StudentService,
        private coureService : CourseService,
        private router : Router,
        private route : ActivatedRoute
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

    isGettingData = false;
    isUpdating = false;

    data : {
        courses : Course[]
    } = {
        courses : [],
    }

    error = StudentError;

    ngOnInit() : void {
        this.fetchStudent( this.route.snapshot.params['id'] );
        fetchCourses( this.coureService , { data : this.data  , status : this.status } );
    }

    async fetchStudent( studentId : string ) : Promise<void> {
        this.isGettingData = true;

        this.studentService.getStudent( studentId )
        .subscribe({
            next : ( data ) => {
                this.isGettingData = false;

                if( data.id == null ){

                    swal({
                        text : 'Something went wrong!',
                        icon : 'warning',
                    }).then( () => {
                        this.router.navigate(['/students']);
                    })

                }else{
                    let { id , name , gender , dob , phone , attendCourses , education } = data;
                    this.student = {
                        id,
                        name,
                        gender,
                        dob,
                        phone,
                        attendCourses,
                        education
                    } ;
        
                }

            },
            error : ( e ) => console.log( e )
        })
    }

    addAttendCourse( target : string ) : void {
        handleAddCourse( { attendCourses : this.student.attendCourses , target : target} );
    }
    
    shouldCheck( id : string ) : boolean {
        let shouldCheck = false;

       for( let course of this.student.attendCourses ){
          if( course.id == id ){
             shouldCheck = true;
             break;
          }
       }
        

        return shouldCheck;
    }

    handleUpdateStudent( form : NgForm )  : void {

        useValidator( studentValidate , Object.keys(this.student) , Object.values(this.student) );

        if( form.valid && isAllOk('student') ){
            swal({
                text :`Are you sure to update this student?`,
                icon : 'warning',
                buttons : ['No','Yes']
            }).then( isYes => {
                if( isYes ){
                    this.isUpdating = true;
                    this.studentService.updateStudent( this.student )
                    .subscribe({
                        next : ( res ) => {
                            this.isUpdating = false;
                           swal({
                             text : res.msg,
                             icon : res.ok ? 'success' : 'warning'
                           }).then( () => {
                                this.router.navigate(['/students']);
                           });
                        },
                        error : ( e ) => console.log( e )
                    })
                }
            })
        }

    }

    ngOnDestroy() : void {
        resetAllError('student');
    }
}