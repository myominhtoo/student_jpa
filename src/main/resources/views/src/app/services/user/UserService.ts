import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Response } from "src/app/models/Response";
import { User } from "src/app/models/User";

@Injectable({
    providedIn : "root"
})
export default class UserService{

    private BASE_URL = "http://localhost:8080/api";

    constructor( private httpClient : HttpClient ){}

    getUsers( id : string, name : string) : Observable<User[]> {
         
        let target = '';

        if( id != '' && name == '' ){
            target = `?id=${id}`;
        }else if ( id == '' && name != '' ){
            target = `?name=${name}`;
        }else if( id != '' && name != '' ){
            target = `?id=${id}&name=${name}`;
        }else{
            target = '';
        }

        return this.httpClient.get<User[]>(`${this.BASE_URL}/users${target}`);
    }

    getUser( userId : string ) : Observable<User>{
        return this.httpClient.get<User>(`${this.BASE_URL}/users/${userId}`)
    }

    addUser( user : User ) : Observable<Response>{
        return this.httpClient.post<Response>(`${this.BASE_URL}/users` , user );
    }

    deleteUser( userId : string ) : Observable<Response>{
        return this.httpClient.delete<Response>(`${this.BASE_URL}/users/${userId}`);
    }

    updateUser( user : User ) : Observable<Response>{
        return this.httpClient.put<Response>( `${this.BASE_URL}/users/${user.id}` , user );
    }

}