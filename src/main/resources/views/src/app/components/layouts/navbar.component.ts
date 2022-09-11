import { Component, OnInit } from "@angular/core";
import getAuthUser from "src/app/util/getAuthUser";

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

    ngOnInit(): void {
        this.authUser = getAuthUser();
    }

}