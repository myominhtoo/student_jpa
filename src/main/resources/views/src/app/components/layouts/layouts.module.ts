import { NgModule } from "@angular/core";
import { NavbarComponent } from "./navbar.component";
import { SidebarComponent } from "./sidebar.component";

@NgModule({
    declarations : [
        SidebarComponent,
        NavbarComponent
    ],
    exports : [
        SidebarComponent,
        NavbarComponent
    ]
})
export class LayoutsModule{}