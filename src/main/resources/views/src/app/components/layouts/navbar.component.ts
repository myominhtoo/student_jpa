import { Component , OnInit } from "@angular/core";

@Component({
    selector : 'navbar',
    templateUrl : './navbar.component.html'
})
export class NavbarComponent implements OnInit {

    todayDate : Date = new Date();

    ngOnInit() : void {
        console.log("inited")
    }

}