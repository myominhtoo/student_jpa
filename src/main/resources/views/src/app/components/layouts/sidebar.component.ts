import { Component } from "@angular/core";

@Component({
    selector : 'sidebar',
    templateUrl : './sidebar.component.html',
})
export class SidebarComponent{

    //for sidebar toggles
    showUserBtns : boolean = false;
    showCourseBtns : boolean = false;
    showStudentBtns : boolean = false;


    /*
        target should be ( user | course | student )
    */
    toggleSidebarBtns( target : string ) : void {

        setTimeout(() => {
            switch( target ){
                case 'user' : 
                    this.showUserBtns = !this.showUserBtns;
                    break;
                case 'course' : 
                    this.showCourseBtns = !this.showCourseBtns;
                    break;
                case 'student' : 
                    this.showStudentBtns = !this.showStudentBtns;
                    break;
            }
        } , 30 );

    }
}