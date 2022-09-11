import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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

    search = {
        id : '',
        name : '',
    }

    timeout : ReturnType<typeof setTimeout> | undefined;

    constructor( 
        private userService : UserService,
        private router : Router,
        private route : ActivatedRoute
    ){
        // this.debounce = useDebounce( this.handleSearch , 500 );
    }

    ngOnInit() : void {
       this.fetchUsers();
       this.route.queryParams.subscribe( params => {
            clearTimeout( this.timeout );
            this.timeout = setTimeout( () => this.handleSearch() , 500  );
       })
    }

    fetchUsers() : void {
        this.status.isLoading = true;
        this.userService.getUsers( this.search.id , this.search.name )
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
                        }).then( () => {
                            if(res.ok) this.fetchUsers();
                        })
                    },
                    error : ( e ) => console.log( e )
                });
            }
        });
    }

    handleChange() : void {
        this.router.navigate(['/users'] ,
         { queryParams : {  
            id : this.search.id,
            name : this.search.name
         } } );
    }

    handleSearch() : void {       
        this.fetchUsers();
    }

    handleReset() : void {
        this.search.id = '';
        this.search.name = '';
        this.handleChange();
    }

}