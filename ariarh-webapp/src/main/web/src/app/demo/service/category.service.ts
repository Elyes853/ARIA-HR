import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Category} from "../api/category";
import {environment} from "../../../environments/environment.dev"
import {AppSettings} from "../model/AppSettings";
import {AuthentificationService} from "./authentification.service";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

    apiUrl = environment.apiUrl + AppSettings.API_ENDPOINT;

    constructor(private http:HttpClient, private authService: AuthentificationService) {}

  getCategories():Observable<Category[]>{
      const headers = this.authService.getAuthorizationHeader();

      return this.http.get<Category[]>(`${this.apiUrl}category`, {headers})
  }

  addCategory(category:Category):Observable<Category>{
      const headers = this.authService.getAuthorizationHeader();

      return this.http.post<Category>(`${this.apiUrl}category`,category, {headers})
  }

  getCategoryId(label:string):Observable<any>{
      let params = new HttpParams().set('label', label);
      const headers = this.authService.getAuthorizationHeader();
      return this.http.get<Category>(`${this.apiUrl}category/${label}`, { params: params,headers })
  }


}
