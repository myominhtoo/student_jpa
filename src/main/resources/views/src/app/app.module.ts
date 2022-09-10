import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent , AddCourseComponent , AddUserComponent, 
        StudentsComponent , StudentDetailComponent , AddStudentComponent, 
        UsersComponent , UserDetailComponent , HomeComponent, LoginComponent , CoursesComponent  } from './util/common';
import { LayoutsModule } from './components/layouts/layouts.module';
import { CommonModule } from '@angular/common';

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
    HomeComponent,
    LoginComponent,
    CoursesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    LayoutsModule,
    CommonModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
