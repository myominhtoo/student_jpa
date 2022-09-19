import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import checkAuth from "src/app/util/checkAuth";
import getAuthUser from "src/app/util/getAuthUser";
import swal from "sweetalert";

@Component({
    selector : 'navbar',
    templateUrl : './navbar.component.html'
})
export class NavbarComponent implements OnInit {

    todayDate : Date = new Date();

    authUser : { id : string , name : string , role : number } = {
        id : '',
        name : '',
        role : 0,
    }

    constructor( private router : Router ){}

    ngOnInit(): void {
        if( checkAuth() ){
            this.authUser = getAuthUser();
        }
    }

    handleLogout() : void {

        swal({
            text : 'Are you sure to logout?',
            icon : 'warning',
            buttons : ['No','Yes']
        }).then( isYes => {
            if( isYes ){
                let complete = false;

                try{
                    localStorage.removeItem( window.atob('isLogin') );
                    localStorage.removeItem( window.atob('authUser') );
                    complete = true;
                }catch( e ){
                    complete = false;
                }

                if( complete ){

                    swal({
                        text : 'Successfully Logout!',
                        icon : 'success',
                    }).then( () => {
                        this.router.navigate(['/login']);
                    })

                }

            }
        });

    }

}