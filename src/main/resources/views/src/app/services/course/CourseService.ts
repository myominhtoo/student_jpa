import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Course } from "src/app/models/Course";
import { Response } from "src/app/models/Response";

@Injectable({
    providedIn : "root"
})
export default class CourseService{

    private BASE_URL = "http://localhost:8080/api";

    constructor( private httpClient : HttpClient ){}

    getCourses() : Observable<Course[]>{
        return this.httpClient.get<Course[]>(`${this.BASE_URL}/courses`);
    }

    addCourse( course : Course ) : Observable<Response> {
        return this.httpClient.post<Response>(`${this.BASE_URL}/courses` , course );
    }

}