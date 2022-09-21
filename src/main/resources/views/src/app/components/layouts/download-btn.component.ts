import { Component, Input } from "@angular/core";


@Component({
    selector : 'download-btn',
    template : `
        <a href='D:/datas/reports/user.pdf' class='btn h3' download><i class="fa-solid fa-download"></i></a>    
    `
})
export class DownloadButtonComponent{
    @Input('url') url : string = '';
}