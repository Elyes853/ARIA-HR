import {Component, OnInit} from '@angular/core';
import {CommonModule, NgStyle} from "@angular/common";
import {MarkdownComponent} from "ngx-markdown";
import {CardModule} from "primeng/card";
import {Message} from "../../model/Message";
import {Candidate} from "../../model/Candidat";
import {Conversation} from "../../model/Conversation";
import {ActivatedRoute, Router} from "@angular/router";
import {ConversationService} from "../../service/conversation.service";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";

@Component({
  selector: 'app-show-conversation',
  standalone: true,
    imports: [
        CommonModule,
        NgStyle,
        MarkdownComponent,
        CardModule
    ],
  templateUrl: './show-conversation.component.html',
  styleUrl: './show-conversation.component.scss'
})
export class ShowConversationComponent implements OnInit {

    messages: Message[] = []

    candidate: Candidate | undefined

    conversation!: Conversation

    conversationId !: number | undefined

    token!: string

    constructor(private route: ActivatedRoute, private conversationService: ConversationService) {

    }

    ngOnInit() {
        const candidateId: string | null = this.route.snapshot.paramMap.get('candidateId')
        console.log(candidateId)


        if (candidateId) {
            this.conversationService.showConversation(+candidateId).subscribe(
                {next:messageList=>{
                        messageList = messageList.splice(2)
                        this.messages = messageList;

                    }}
            )
        }

    }

}
