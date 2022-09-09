import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent , AddCourseComponent , AddUserComponent, 
        StudentsComponent , StudentDetailComponent , AddStudentComponent, 
        UsersComponent , UserDetailComponent  } from './util/common';

@NgModule({
  declarations: [
    AppComponent,
    AddCourseComponent,
    AddStudentComponent,
    StudentsComponent,
    StudentDetailComponent,
    AddUserComponent,
    UsersComponent,
    UserDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
