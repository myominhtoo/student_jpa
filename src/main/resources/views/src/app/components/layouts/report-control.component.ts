import { Component, EventEmitter, Input, Output } from "@angular/core";

@Component({
    selector : 'report-control',
    template : `
        <div class='dropdown' id='dropdown'>
            <button class='btn btn-success btn-sm dropdown-toggle' data-bs-toggle='dropdown' data-bs-target='#dropdown'>{{ isReporting ? 'Reporting' : 'Report To'}}</button>
            <ul class="dropdown-menu p-0">
                <li (click)="makeReport.emit({ type : 'html' , target : target  })" class="dropdown-item w-100">HTML</li>
                <li (click)="makeReport.emit({ type : 'pdf' , target : target  })" class="dropdown-item w-100">PDF</li>
                <li (click)="makeReport.emit({ type : 'excel' , target : target  })" class="dropdown-item w-100">EXCEL</li>
            </ul>
        </div>
    `,
})
export class ReportControlComponent{

    @Input('target') target : string = '';
    @Input('isReporting') isReporting : boolean = false;
    
    @Output('make-report') makeReport = new EventEmitter();
    
}