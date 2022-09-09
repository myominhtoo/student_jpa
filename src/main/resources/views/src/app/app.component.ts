import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'views';

  count = 0 ;

  courses = [];

  increment():void{
    this.count++;
  }

  decrement():void{
    this.count--;
  }

  ngOnInit(): void {
     this.fetchCourses();
  }

 async fetchCourses(){
   fetch("http://localhost:8080/api/courses")
   .then( res => res.json() )
   .then( data => {
    this.courses = data;
   })
 }

}
