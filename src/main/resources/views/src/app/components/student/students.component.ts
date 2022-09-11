import { Component , OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Status } from 'src/app/models/Status';
import { Student } from 'src/app/models/Student';
import StudentService from 'src/app/services/student/StudentService';
import checkAuth from 'src/app/util/checkAuth';
import swal from 'sweetalert';

@Component({
    selector : 'students',
    templateUrl : './students.component.html',
})
export class StudentsComponent implements OnInit {

    students : Student[] = [];

    columns : string[] = [
        'Id',
        'Name',
        'Attend Courses'
    ]

    status : Status = {
        isBlank : false,
        isLoading : false
    }

    search = {
        id : '',
        name : '',
        course : '',
    }

    timeout : ReturnType<typeof setTimeout> | undefined;
    
    
    constructor( 
        private studentService : StudentService ,
        private router : Router ,
        private route : ActivatedRoute  
    ){}

    ngOnInit(): void {

       if( checkAuth() ){

            this.fetchStudents();

            this.route.queryParams.subscribe( params => {
                clearTimeout( this.timeout );
                this.timeout  = setTimeout( () => this.fetchStudents() , 500 );
            })

       }else{

            this.router.navigate( ['/login'] , {
                queryParams : {
                    msg : 'Please login to continue!',
                }

            });

       }

    }

    fetchStudents() : void {
        this.status.isLoading = true;

        this.studentService.getStudents( this.search.id , this.search.name , this.search.course )
        .subscribe({
            next : ( datas ) => {
                this.status.isLoading = false;

                if( datas.length == 0 ) this.status.isBlank = true;
                else this.status.isBlank = false;

                this.students = datas;

            }
        })
    }

    handleDeleteStudent( studentId : string ) : void {
        swal({
            text : `Are you sure to delete ${studentId} course ? `,
            icon : 'warning',
            buttons : ['No','Yes'],
        }).then( isYes => {
            if(isYes){
                this.studentService.deleteStudent( studentId )
                .subscribe({
                    next : ( res ) => {
                        swal({
                            text : res.msg,
                            icon : res.ok ? 'success' : 'warning'
                        }).then( () => {
                            if( res.ok ) this.fetchStudents();
                        })
                    },
                    error : ( e ) => console.log( e )
                });
            }
        });
    }

    handleChange() : void {
        this.router.navigate( ['/students'] , {
            queryParams : {
                id : this.search.id,
                name : this.search.name,
                course : this.search.course
            }                           
        } );
    }

    handleReset() : void {
        this.search = { id : '' , name : '' ,course : ''};
        this.fetchStudents();
    }
                                 
}