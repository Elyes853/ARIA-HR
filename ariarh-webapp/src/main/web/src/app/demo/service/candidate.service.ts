import { Injectable } from '@angular/core';
import {Candidate} from "../model/Candidat";
import {environment} from "../../../environments/environment";
import {AppSettings} from "../model/AppSettings";
import {HttpClient} from "@angular/common/http";
import {AuthentificationService} from "./authentification.service";

@Injectable({
  providedIn: 'root'
})
export class CandidateService {
    private apiUrl = environment.apiUrl + AppSettings.API_ENDPOINT


    constructor(private http:HttpClient , private  authService : AuthentificationService) { }
    getCandidatesPerOffer(id: number) {
        const headers = this.authService.getAuthorizationHeader();
        const url = `${this.apiUrl}offers/${id}/candidates`;
        return this.http.get<Candidate[]>(url , { headers });
    }

    getCandidateById(id:number){
        const headers = this.authService.getAuthorizationHeader();
        const url = `${this.apiUrl}candidates/${id}`;
        return this.http.get<Candidate>(url , { headers });

    }

/*    getCandidateCV(id:number){
        const headers = this.authService.getAuthorizationHeader();
        const url = `${this.apiUrl}candidates/${id}/cv`;
        return this.http.get<any>(url , { headers });

    }*/

    getCandidateCV(id:number){
        const headers = this.authService.getAuthorizationHeader();
        const url = `${this.apiUrl}candidates/${id}/cv`;
        return this.http.get(url , { headers, responseType: 'blob' });
    }



}
