import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthentificationService} from "./authentification.service";
import {Message} from "../model/Message";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {AppSettings} from "../model/AppSettings";
import {Conversation} from "../model/Conversation";



@Injectable({
  providedIn: 'root'
})
export class ConversationService {
    private apiUrl = environment.apiUrl + AppSettings.API_ENDPOINT
  constructor(private http:HttpClient, private autheService: AuthentificationService) { }

    sendAndRecieveMessage(message: Message):Observable<Message>{
      const headers = this.autheService.getAuthorizationHeader();
      console.log(message)
      return this.http.post<Message>(this.apiUrl+`conversation/message`, message, { headers });
    }

    createConversation(jwt:string):Observable<Conversation>{
        let params = new HttpParams()
            .set('token', jwt)
        const headers = this.autheService.getAuthorizationHeader();
        return this.http.post<Conversation>(this.apiUrl+`conversation`,{},{params, headers });
    }

    showConversation(id:number):Observable<Message[]>{
        const headers = this.autheService.getAuthorizationHeader();
        return this.http.get<Message[]>(this.apiUrl+`conversation/${id}`,{headers});

    }
}
