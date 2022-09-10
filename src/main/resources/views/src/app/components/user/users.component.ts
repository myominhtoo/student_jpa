import { Component } from '@angular/core';
import { Course } from 'src/app/models/Course';
import { Student } from 'src/app/models/Student';

@Component({
    selector : 'users',
    templateUrl : './users.component.html',
})
export class UsersComponent{


    columns : string[]  = [ 'id' , 'name' , 'attend courses' ];

    // datas : Course[] = [
    //     {
    //         id : "C0001",
    //         name : "Java",
    //     },
    //     {
    //         id : "C002",
    //         name : "php",
    //     }
    // ]
    
    datas : Student[] = [
        {
            id : "S001",
            name : "Student 1",
            dob : "2003-01-01",
            education : 1,
            gender : 0,
            attendCourses : [
                {
                    id  : "C001",
                    name : "java"
                },
                {
                    id  : "C001",
                    name : "php"
                }
            ],
        }
    ]

    target : string = 'student';


}