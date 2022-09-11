import { Component, OnInit } from '@angular/core';
import { Course } from 'src/app/models/Course';
import { Status } from 'src/app/models/Status';
import { Student } from 'src/app/models/Student';
import { User } from 'src/app/models/User';
import UserService from 'src/app/services/user/UserService';
import swal from 'sweetalert';

@Component({
    selector : 'users',
    templateUrl : './users.component.html',
})
export class UsersComponent implements OnInit{

    users : User[] = [];

    status : Status  = {
        isBlank : false,
        isLoading : false,
    }

    columns : string[] = [
        'Id',
        'Name',
        'Email'
    ]

    constructor( private userService : UserService ){}

    ngOnInit() : void {
       this.fetchUsers();
    }

    fetchUsers() : void {
        this.status.isLoading = true;
        this.userService.getUsers()
        .subscribe({
            next : ( datas ) => {
                this.status.isLoading = false;

                if( datas.length == 0 ) this.status.isBlank = true;
                else this.status.isBlank = false;

                this.users = datas;
            },
            error : ( e ) => console.log( e )
        })
    }

    handleDeleteUser( userId : string ){
        swal({
            text : `Are you sure to delete ${userId} course ? `,
            icon : 'warning',
            buttons : ['No','Yes'],
        }).then( isYes => {
            if(isYes){
                this.userService.deleteUser( userId )
                .subscribe({
                    next : ( res ) => {
                        swal({
                            text : res.msg,
                            icon : res.ok ? 'success' : 'warning'
                        });
                        if( res.ok ) this.fetchUsers();
                    },
                    error : ( e ) => console.log( e )
                });
            }
        });
    }

}