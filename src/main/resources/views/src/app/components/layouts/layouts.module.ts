import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { AppRoutingModule } from "src/app/app-routing.module";
import { DownloadButtonComponent } from "./download-btn.component";
import { FooterComponent } from "./footer.component";
import { NavbarComponent } from "./navbar.component";
import { ReportControlComponent } from "./report-control.component";
import { SidebarComponent } from "./sidebar.component";
import { TableComponent } from "./table.component";

@NgModule({
    declarations : [
        SidebarComponent,
        NavbarComponent,
        FooterComponent,
        TableComponent,
        ReportControlComponent,
        DownloadButtonComponent,
    ],
    imports : [
        AppRoutingModule,
        BrowserModule
    ],
    exports : [
        SidebarComponent,
        NavbarComponent,
        TableComponent,
        ReportControlComponent,
        DownloadButtonComponent
    ]
})
export class LayoutsModule{}