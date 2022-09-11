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

@Component({
    selector : 'add-student',
    templateUrl : './add-student.component.html',
})
export class AddStudentComponent implements OnInit {

    constructor(
         private studentService : StudentService,
         private coureService : CourseService
    ){}

    student : Student = {
        id : '',
        name : '',
        dob : '',
        gender : 0,
        education : 0,
        attendCourses : [],
    }

    status : Status = {
        isBlank : false,
        isLoading : false,
    }

   data : {
        courses : Course[]
   } = {
        courses : [],
   }

   error = StudentError;

    ngOnInit() : void {
        fetchCourses( this.coureService , { data : this.data , status : this.status } );// getting courses required for student 

    }

    addAttendCourse( target : string ) : void {
        handleAddCourse( { attendCourses : this.student.attendCourses , target : target} );
    }


    handleAddStudent( form : NgForm ) : void {
       let keys = Object.keys( this.student );
       let values = Object.values( this.student );

       useValidator( studentValidate , keys , values  );

    //    console.log( this.error )
    console.log( this.student )
    }

}