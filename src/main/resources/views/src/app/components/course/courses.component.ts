import { Component , OnInit } from '@angular/core';
import { Course } from 'src/app/models/Course';
import { Status } from 'src/app/models/Status';
import CourseService from 'src/app/services/course/CourseService';
import swal from 'sweetalert';

@Component({
    selector : 'courses',
    templateUrl : './courses.component.html',
})
export class CoursesComponent implements OnInit {

    courses : Course[] = []; 

    columns : string[] = [
        "Id",
        "Name",
    ]

    status : Status = {
        isBlank : false,
        isLoading : false
    }

    constructor( private courseService : CourseService ){}

    ngOnInit() : void {
        this.fetchCourses();
    }

    fetchCourses() : void {
        this.status.isLoading = true;

        this.courseService.getCourses()
        .subscribe({
            next : ( datas ) => {
                this.status.isLoading = false;

                if( datas.length == 0 ) this.status.isBlank = true;
                else this.status.isBlank = false;

                this.courses = datas;
            },
            error : ( e ) => console.log( e )
         })
    }

    handleDeleteCourse( courseId : string ) : void {
        swal({
            text : `Are you sure to delete ${courseId} course ? `,
            icon : 'warning',
            buttons : ['No','Yes'],
        }).then( isYes => {
            if(isYes){
                
            }
        });
    }

}