import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "src/app/models/User";

@Injectable({
    providedIn : "root"
})
export default class UserService{

    private BASE_URL = "http://localhost:8080/api";

    constructor( private httpClient : HttpClient ){}

    getUsers() : Observable<User[]> {
        return this.httpClient.get<User[]>(`${this.BASE_URL}/users`);
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