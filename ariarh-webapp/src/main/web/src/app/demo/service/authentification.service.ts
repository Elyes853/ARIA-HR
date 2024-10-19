// authentification.service.ts
import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse, HttpParams, HttpHeaders} from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../model/User';

@Injectable({
    providedIn: 'root'
})
export class AuthentificationService {
    constructor(private http: HttpClient) { }

    login(username: string, password: string): Observable<HttpResponse<User>> {
        const body = new HttpParams()
            .set('username', username)
            .set('password', password);

        return this.http.post<User>(`${environment.apiUrl}/api/authenticate`, body, { observe: 'response' });
    }

    logout() {
        localStorage.removeItem('currentUser');
        setTimeout(() => {
            localStorage.removeItem('token');
        }, 1000);
    }

    isAuthenticated(): boolean {
        // Check if the user is authenticated based on your implementation
        // For example, you can check if the user token exists in localStorage
        return !!localStorage.getItem('token');
    }
    getAuthorizationHeader(): HttpHeaders {
        const token = localStorage.getItem('token');
        return new HttpHeaders().set('Authorization', `${token}`);
    }
}
