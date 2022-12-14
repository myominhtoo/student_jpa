import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {  AddCourseComponent , AddUserComponent,  StudentsComponent ,
   StudentDetailComponent , AddStudentComponent, 
  UsersComponent , UserDetailComponent , HomeComponent ,LoginComponent, CoursesComponent  } from './util/common';

const routes: Routes = [
  {
     path : '',
     component : HomeComponent,
  },
  {
    path : 'login',
    component : LoginComponent
  },
  {
    path : 'users',
    component : UsersComponent,
  },
  {
    path : 'add-user',
    component : AddUserComponent
  },
  {
    path : 'users/:id',
    component : UserDetailComponent
  },
  {
    path : 'students',
    component : StudentsComponent,
  },
  {
    path : 'add-student',
    component : AddStudentComponent,
  },
  {
    path : 'students/:id',
    component : StudentDetailComponent
  },
  {
    path : 'add-course',
    component : AddCourseComponent
  },
  {
    path : 'courses',
    component : CoursesComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
