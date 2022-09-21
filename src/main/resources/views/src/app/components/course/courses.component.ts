import { Component , OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Course } from 'src/app/models/Course';
import { Report } from 'src/app/models/Report';
import { Status } from 'src/app/models/Status';
import CourseService from 'src/app/services/course/CourseService';
import ReportService from 'src/app/services/report/ReportService';
import checkAuth from 'src/app/util/checkAuth';
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

    report : Report = {
        isReporting : false,
        ok : false,
        downloadUrl :'',
    }

    constructor( private courseService : CourseService , private router : Router , private reportService : ReportService ){}

    ngOnInit() : void {

        if( checkAuth() ){
            
            this.fetchCourses();

        }else{

            this.router.navigate( ['/login'] , {
                queryParams : {
                    msg : 'Please login to continue!',
                }  
             });

        }

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
                this.courseService.deleteCourse( courseId )
                .subscribe({
                    next : ( res ) => {
                        swal({
                            text : res.msg,
                            icon : res.ok ? 'success' : 'warning'
                        });
                    },
                    error : ( e ) => console.log( e )
                });
            }
        });
    }

    handleReport( payload : { type : string , target : string } ){
        this.report.isReporting = true;
        this.reportService._report( payload.type , payload.target )
        .subscribe({
            next : ( res ) => {
                this.report.isReporting = false;

                if( res.ok ){
                    // this.report.ok = true;
                    // this.report.downloadUrl = 'file:///'+res.msg;
                    // console.log(this.report.downloadUrl)
                    swal({
                        text : `Successfully Reported! Check ${res.msg}`,
                        icon : 'success'
                    });
                }else{
                    swal({
                        text : res.msg,
                        icon : 'warning',
                    });
                }

            },
            error : e => console.log(e)
        });
    }

}