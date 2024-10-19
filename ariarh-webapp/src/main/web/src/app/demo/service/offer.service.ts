import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Offer} from "../api/offer";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {AppSettings} from "../model/AppSettings";
import {AuthentificationService} from "./authentification.service";
import {Candidate} from "../model/Candidat";

@Injectable({
  providedIn: 'root'
})
export class OfferService {
    private apiUrl = environment.apiUrl + AppSettings.API_ENDPOINT

  constructor(private http:HttpClient , private  authService : AuthentificationService) { };

  getOffers(): Observable<Offer[]>{
    return this.http.get<Offer[]>(this.apiUrl+'offers' );
  }

  addOffer(offer:Offer){
      const headers = this.authService.getAuthorizationHeader();
      return this.http.post<Offer>(this.apiUrl+'offer',offer , { headers })
  }


    getOfferById(id: number) {
        const headers = this.authService.getAuthorizationHeader();
        const url = `${this.apiUrl}offers/${id}`;
        return this.http.get<Offer>(url , { headers });
        }




}
