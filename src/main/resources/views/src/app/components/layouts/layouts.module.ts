import { NgModule } from "@angular/core";
import { AppRoutingModule } from "src/app/app-routing.module";
import { NavbarComponent } from "./navbar.component";
import { SidebarComponent } from "./sidebar.component";

@NgModule({
    declarations : [
        SidebarComponent,
        NavbarComponent
    ],
    imports : [
        AppRoutingModule
    ],
    exports : [
        SidebarComponent,
        NavbarComponent
    ]
})
export class LayoutsModule{}