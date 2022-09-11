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

    getStudents( id : string , name : string , course : string ) : Observable<Student[]> {

        let target = '';

        if( id != '' && name == '' && course == '' ){
            target = `?id=${id}`;
        }else if( id == '' && name != '' && course == '' ){
            target = `?name=${name}`;
        }else if( id == '' && name == '' && course != '' ){
            target = `?course=${course}`;
        }else if( id  != '' && name != '' && course == '' ){
            target = `?id=${id}&name=${name}`;
        }else if( id != '' && name == '' && course != '' ){
            target = `?id=${id}&course=${course}`;
        }else if( id == '' && name != '' && course != ''  ){
            target = `?name=${name}&course=${course}`;
        }else if( id != '' && name != '' && course != '' ){
            target = `?id=${id}&name=${name}&course=${course}`;
        }else{
            target = '';
        }

       return  this.httpClient.get<Student[]>(`${this.BASE_URL}/students${target}`);
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


    getId() : Observable<Response> {
        return this.httpClient.get<Response>( `${this.BASE_URL}/students/id`);
    }

}