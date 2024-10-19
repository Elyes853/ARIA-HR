import {Component, HostListener, OnInit} from '@angular/core';
import {CardModule} from "primeng/card";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {InputTextareaModule} from "primeng/inputtextarea";
import {Conversation} from "../../model/Conversation";
import {ConversationService} from "../../service/conversation.service";
import {Candidate} from "../../model/Candidat";
import {Message} from "../../model/Message";
import {MarkdownComponent} from "ngx-markdown";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-conversation',
  standalone: true,
    imports: [
        CommonModule,
        CardModule,
        FormsModule,
        InputTextareaModule,
        MarkdownComponent
    ],
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.scss'
})
export class ConversationComponent implements OnInit{

    messages :Message[] = []

    candidate : Candidate | undefined

    conversation!: Conversation

    conversationId !: number | undefined

    token! :string

    displayedMessages:Message[] = []

/*    @HostListener('window:focus', ['$event'])
    onFocus(event: any): void {
        console.log('Focused');

        // Do something when the window is focused
    }

    @HostListener('window:blur', ['$event'])
    onBlur(event: any): void {
        console.log('Blurred');
        alert("Vous avez cliqué en dehors du navigateur, la prochaine fois la session se terminera!")
        // Do something when the window is blurred
    }*/




    constructor(private route: ActivatedRoute, private conversationService:ConversationService, private router:Router) {
    }
    ngOnInit(){
/*
        this.conversation = new Conversation({candidate:{id:3}});
*/

        this.route.queryParams.subscribe(params => {
            this.token! = params['token'];
            this.conversationService.createConversation(this.token)
                .subscribe({
                    next:conversation =>{
                    console.log(conversation.messagesList)
                    this.conversationId = conversation.id
                    console.log(conversation.id)
                    this.conversation = conversation;
                    this.messages = this.conversation!.messagesList!.slice(2);


                },
                    error:error => {
                        console.log(error);
                        console.log(error.message)
                        this.router.navigate(['/notfound'])
                    }
                })
        })

/*
        this.conversationService.createConversation(this.conversation)
            .subscribe(conversation =>{
                this.conversationId = conversation.id
                console.log(conversation.id)
                this.conversation = conversation
            })*/
    }




    addMessage(event: any) {

        //make content a string
        const inputElement = event.target as HTMLInputElement;

        // Extract the value and ensure it's treated as a string
        const content: string = inputElement.value;

        this.messages.push({role: 'user', content: content})

        console.log(content);

        let message = new Message({role:"user", content:content, conversation_id:this.conversation})
        setTimeout(() => {
            const lastMessage = document.getElementById('lastMessage');
            if (lastMessage) {
                lastMessage.scrollIntoView({ behavior: 'smooth' });
            }
        }, 0);

        //make the input empty again
        event.target.value = '';
        this.conversationService.sendAndRecieveMessage(message)
            .subscribe(
                message =>{
                    console.log(message)

                    this.messages.push({role: 'assistant', content: message.content})

                    setTimeout(() => {
                        const lastMessage = document.getElementById('lastMessage');
                        if (lastMessage) {
                            lastMessage.scrollIntoView({ behavior: 'smooth' });
                        }
                    }, 0);
                }
            )

    }

    getChatBotResponse(): string {
        //coté logique du chatbot
        return "This is a simulated response from the chatbot.";
    }


}
