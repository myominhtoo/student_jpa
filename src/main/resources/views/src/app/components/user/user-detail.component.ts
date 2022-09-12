import { Component , OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import UserError from 'src/app/models/error/UserError';
import { Status } from 'src/app/models/Status';
import { User } from 'src/app/models/User';
import UserService from 'src/app/services/user/UserService';
import checkAuth from 'src/app/util/checkAuth';
import getAuthUser from 'src/app/util/getAuthUser';
import resetAllError from 'src/app/util/resetErrors';
import checkPassword from 'src/app/util/validator/checkPassword';
import isAllOk from 'src/app/util/validator/isAllOk';
import useValidator from 'src/app/util/validator/useValidator';
import { userValidate } from 'src/app/util/validator/validators';
import swal from 'sweetalert';

@Component({
    selector : 'user-detail',
    templateUrl : './user-detail.component.html',
})
export class UserDetailComponent implements OnInit {

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
        isBlank : false,
    }

    error = UserError ;

    isGettingUser : boolean = false;

    constructor(
        private userService : UserService,
        private router : Router,
        private route : ActivatedRoute
    ){}


    ngOnInit() : void {
        if( checkAuth() ){
            let userId = this.route.snapshot.params["id"];

             this.fetchUser( userId );
        }else{
            
            this.router.navigate( ['/login'] , {
                queryParams : {
                    msg : 'Please login to continue!',
                }
    
             } );

        }
    }

    async fetchUser( userId : string ) : Promise<void> {

        this.isGettingUser = true;
        this.userService.getUser( userId )
        .subscribe({
            next : ( data ) => {
                this.isGettingUser = false;

                if( data.id == null ){

                    swal({
                        text : 'Something went wrong!',
                        icon : 'warning',
                        dangerMode : true
                    }).then( () => {
                        this.router.navigate(['/users']);
                    });

                }
                else{
                    
                    //to prevent confirm changing to confirmPassword  #my naming fauld
                    let { id , name , password , role , email } = data;
                    this.user = {
                        id , 
                        name , 
                        email,
                        password,
                        role,
                        confirm : ''
                    };
                    console.log(this.user)
                }
            },
            error : ( e ) => console.log( e )
        })

    }

    handleUpdateUser( form : NgForm ) : void {

        let keys = Object.keys(this.user);
        let values = Object.values(this.user);
        
        useValidator( userValidate , keys , values );//custom validating for user

        if( form.valid && isAllOk('user') ){
            if( checkPassword( this.user.password , this.user.confirm ) ){
                swal({
                    title : `Are you sure to update ${this.user.name}'s infos?`,
                    icon : 'warning',
                    buttons : ['No','Yes']
                }).then( isYes => {
                    if(isYes){
                        this.userService.updateUser( this.user )
                        .subscribe({
                            next : ( res ) => {
                                if( res.ok ){

                                    if(this.user.id == getAuthUser().id ){
                                        localStorage.setItem(window.atob('authUser'),JSON.stringify({
                                            id : this.user.id,
                                            name : this.user.name,
                                            role : this.user.role,
                                        }));
                                    }

                                    swal({
                                        text : res.msg,
                                        icon : 'success'
                                    }).then( () => {
                                        this.router.navigate(['/users']);
                                    });
                                }
                                else{
                                    swal({
                                        text : res.msg,
                                        icon : 'danger',
                                    })
                                }
                            },
                            error : ( e ) => console.log( e )
                        })
                    }
                })
            }
        }

    }
    

    ngOnDestroy() : void {
        resetAllError('user');
    }

}