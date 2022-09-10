import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { AppRoutingModule } from "src/app/app-routing.module";
import { FooterComponent } from "./footer.component";
import { NavbarComponent } from "./navbar.component";
import { SidebarComponent } from "./sidebar.component";
import { TableComponent } from "./table.component";

@NgModule({
    declarations : [
        SidebarComponent,
        NavbarComponent,
        FooterComponent,
        TableComponent
    ],
    imports : [
        AppRoutingModule,
        BrowserModule
    ],
    exports : [
        SidebarComponent,
        NavbarComponent,
        TableComponent
    ]
})
export class LayoutsModule{}