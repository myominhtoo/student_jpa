import { Component , OnInit } from '@angular/core';
import { Status } from 'src/app/models/Status';
import { Student } from 'src/app/models/Student';
import StudentService from 'src/app/services/student/StudentService';
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

    constructor( private studentService : StudentService ){}

    ngOnInit(): void {
        this.fetchStudents();
    }

    fetchStudents() : void {
        this.status.isLoading = true;

        this.studentService.getStudents()
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
                        });
                        if( res.ok ) this.fetchStudents();
                    },
                    error : ( e ) => console.log( e )
                });
            }
        });
    }

}