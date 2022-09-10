import { Component , Input } from "@angular/core";

@Component({
    selector : 'Table',
    templateUrl : './table.component.html',
})
export class TableComponent {


    @Input('columns') columns : string[] = [];

    @Input('datas') datas : any[] = [];

    @Input('target') target : string = '';


}