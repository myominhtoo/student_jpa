import { HttpClient, HttpParams  } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Response } from 'src/app/models/Response';

@Injectable({
    providedIn : 'root'
})
export default class ReportService{
    
    constructor( private httpClient : HttpClient ){}

    public _report( type : string , target : string ) : Observable<Response>{
        let params = new HttpParams()
                        .set('type',type)
                        .set('target',target);

        return this.httpClient.get<Response>(`http://localhost:8080/api/report` , { params } );
    }

}