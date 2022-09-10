import { Component , Input } from "@angular/core";
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

}