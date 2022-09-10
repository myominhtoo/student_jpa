import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Response } from "src/app/models/Response";
import { Student } from "src/app/models/Student";

@Injectable({
    providedIn : "root"
})
export default class StudentService{

    private  BASE_URL = "http://localhost:8080/api";

    constructor( private httpClient : HttpClient ){}

    getStudents() : Observable<Student[]> {
       return  this.httpClient.get<Student[]>(`${this.BASE_URL}/students`);
    }

    getStudent( studentId : string ) : Observable<Student> {
        return this.httpClient.get<Student>(`${this.BASE_URL}/students/${studentId}`);
    }

    addStudent( student : Student ) : Observable<Response> {
        return this.httpClient.post<Response>( `${this.BASE_URL}/students` , student );
    }

    deleteStudent( studentId : string ) : Observable<Response>{
        return this.httpClient.delete<Response>(`${this.BASE_URL}/students/${studentId}`);
    }

    updateStudent( student : Student ) : Observable<Response> {
        return this.httpClient.put<Response>( `${this.BASE_URL}/students/${student.id}` , student );
    }

}