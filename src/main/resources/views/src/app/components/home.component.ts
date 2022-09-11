import { Component , OnInit  } from '@angular/core';
import { Router } from '@angular/router';
import checkAuth from '../util/checkAuth';
import getAuthUser from '../util/getAuthUser';

@Component({
    selector : 'home',
    templateUrl : './home.component.html',
})
export class HomeComponent implements OnInit {

    authUser : { 
        id : string ,
        name : string,
        role : number
    } = {
        id : '',
        name : '',
        role : 0
    }

    constructor( private router : Router ){}

    ngOnInit(): void {
       if( checkAuth() ){

         this.authUser = getAuthUser();

       }else{
        
         this.router.navigate( ['/login'] , {
            queryParams : {
                msg : 'Please login to continue!',
            }

         } );
       }
    }

}