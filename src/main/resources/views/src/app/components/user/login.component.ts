import { Component } from '@angular/core';
import { User } from 'src/app/models/User';
import UserError from 'src/app/models/error/UserError';
import UserService from 'src/app/services/user/UserService';
import { NgForm } from '@angular/forms';
import useValidator from 'src/app/util/validator/useValidator';
import { userValidate } from 'src/app/util/validator/validators';
import isAllOk from 'src/app/util/validator/isAllOk';
import { Status } from 'src/app/models/Status';
import swal from 'sweetalert';
import { Router } from '@angular/router';

@Component({
    selector : 'login',
    templateUrl : './login.component.html',
})
export class LoginComponent {

    user : User = {
        id : '',
        name : '',
        email : '',
        password : '',
        confirm : '',
        role : 0
    }
    
    status : Status = {
        isLoading : false,
        isBlank : false
    }

    error = UserError ; 

    constructor( private userService : UserService ,private router : Router  ){}

    handleLogin( form : NgForm ) : void {

        let keys = ['id','password'];
        let values = [ this.user.id , this.user.password ];

        useValidator( userValidate , keys , values );

        if( form.valid && isAllOk('user')){
            this.status.isLoading = true;
            this.userService.getUser( this.user.id )
            .subscribe({
                next : ( data ) =>{
                    this.status.isLoading = false;

                    if( data.id == null ){
                        this.error.id = { hasError : true , msg : `Invalid user's id!` };
                    }else{
                        if( this.user.password == data.password ){
                            localStorage.setItem( window.atob('isLogin') , window.atob('true') );
                            localStorage.setItem( window.atob('authUser') , JSON.stringify({ id : data.id , name : data.name , role : data.role }) );
                            swal({
                                text : 'Successfully Loggined!',
                                icon : 'success'
                            }).then( () => {
                                this.router.navigate(['/']);
                            })

                        }else{
                            this.error.password= { hasError : true , msg : `Invalid password!` };
                        }
                    }

                },
                error : ( e ) => console.log( e ) 
            })

        }

    }

}