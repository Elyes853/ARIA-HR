import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {User} from "../../model/User";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {AppSettings} from "../../model/AppSettings";
import {AuthentificationService} from "../authentification.service";
import {Candidate} from "../../model/Candidat";
import {Offer} from "../../api/offer";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private userSource = new BehaviorSubject<User|null>(null);
    currentUser = this.userSource.asObservable();
    private url = environment.apiUrl + AppSettings.API_ENDPOINT ;
    constructor(private httpClient :  HttpClient , private  authService: AuthentificationService) { }

    updateUser(user: User) {
        this.userSource.next(user);
    }
    create(user: Candidate, offerId: number) {
        let headers = this.authService.getAuthorizationHeader();
        headers = headers.set('X-action', 'create');

        const requestBody = {
            user: user,
            offerId: offerId
        };

        return this.httpClient.post<any>(this.url + 'addCandidat', requestBody, {
            headers: headers,
            observe: 'response'
        });
    }

}
