import { Component } from '@angular/core';
import { User } from 'src/app/models/User';
import { NgForm } from '@angular/forms';
import useValidator from 'src/app/util/validator/useValidator';
import { userValidate } from 'src/app/util/validator/validators';
import UserError from 'src/app/models/error/UserError';
import UserService from 'src/app/services/user/UserService';
import checkPassword from 'src/app/util/validator/checkPassword';
import swal from 'sweetalert';
import { Router } from '@angular/router';
import { Status } from 'src/app/models/Status';
import isAllOk from 'src/app/util/validator/isAllOk';

@Component({
    selector : 'add-user',
    templateUrl  : './add-user.component.html',
})
export class AddUserComponent {

    user : User = {
        id : 'clone',//clone for validating to pass
        name : '',
        email : '',
        password : '',
        confirm : '',
        role : -1
    }

    status : Status = {
        isLoading : false,
        isBlank : false,
    }

    error = UserError;

    constructor( private userService : UserService , private router : Router ){} 


    handleAddUser( form : NgForm ) : void {

        /* 
            must order values with key to use useValidator
        */
        let errorKeys = Object.keys(this.user);
        let values = Object.values(this.user);
        
        useValidator( userValidate , errorKeys , values );//custom validating for user



        if( form.valid && isAllOk('user') ){
            if( checkPassword( this.user.password , this.user.confirm ) ){
                this.status.isLoading = true;

                this.userService.addUser( this.user )
                .subscribe({
                     next : ( res ) => {
                        this.status.isLoading = false;

                         if( res.ok ){

                            swal({
                                text : res.msg,
                                icon : 'success'
                            }).then(() => {
                                this.router.navigate( ['/users'] );
                            })

                         }else{
                            this.error.email = { hasError : true , msg : res.msg };
                         }
                         
                     },  
                     error : ( e ) => console.log( e )
                });

            }
        }
    }

}