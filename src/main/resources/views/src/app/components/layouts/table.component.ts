import { Component , EventEmitter, Input, Output } from "@angular/core";
import { Status } from "src/app/models/Status";

@Component({
    selector : 'Table',
    templateUrl : './table.component.html',
})
export class TableComponent {


    @Input('columns') columns : string[] = [];

    @Input('datas') datas : any[] = [];

    @Input('target') target : string = '';

    @Input('status') status : Status = {
        isLoading : false,
        isBlank : false
    }

    //output emits
    @Output('delete-course') deleteCourse = new EventEmitter();

    @Output('delete-user') deleteUser = new EventEmitter();

    @Output('delete-student') deleteStudent = new EventEmitter();
}