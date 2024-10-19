import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.dev";
import {Observable} from "rxjs";
import {Question} from "../api/question";
import {AppSettings} from "../model/AppSettings";
import {AuthentificationService} from "./authentification.service";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

    apiUrl = environment.apiUrl + AppSettings.API_ENDPOINT;

    constructor(private http:HttpClient, private authService:AuthentificationService) {}

    getQuestions():Observable<Question[]>{
        const headers = this.authService.getAuthorizationHeader();
        return this.http.get<Question[]>(`${this.apiUrl}question` , {headers})
    }

    addQuestion(question:Question):Observable<Question>{
        const headers = this.authService.getAuthorizationHeader();
        return this.http.post<Question>(`${this.apiUrl}question`,question, {headers})
    }

}
