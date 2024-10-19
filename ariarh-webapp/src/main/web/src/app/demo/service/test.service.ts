import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Test} from "../api/test";
import {environment} from "../../../environments/environment.dev";
import {AuthentificationService} from "./authentification.service";
import {AppSettings} from "../model/AppSettings";

@Injectable({
  providedIn: 'root'
})
export class TestService {

    apiUrl = environment.apiUrl + AppSettings.API_ENDPOINT;

    constructor(private  authService : AuthentificationService, private http:HttpClient) { }
    addTest(test:Test):Observable<Test>{
        const headers = this.authService.getAuthorizationHeader();
        return this.http.post<Test>(`${this.apiUrl}test`,test,{headers})
    }


}
