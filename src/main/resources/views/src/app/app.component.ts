import { Component } from '@angular/core';
import { Course } from './models/Course';
import CourseService from './services/course/CourseService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'views';

  courses : Course[] = [];

  constructor( private courseService : CourseService ){}

  // async fetchCourses(){
  //   this.courseService.getCourses()
  //   .subscribe({
  //     next : ( data ) => {
  //       console.log( data );
  //       this.courses = data;
  //     },
  //     error : ( e ) => console.log( e )
  //   })
  // }

  // ngOnInit(): void {
  //     this.fetchCourses();
  // }

}
