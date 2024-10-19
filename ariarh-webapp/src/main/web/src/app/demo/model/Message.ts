import {Conversation} from "./Conversation";
import {Timestamp} from "rxjs";

export class Message {
    id?: number;
    role!: string;
    content!:string;
    conversation_id ?: Conversation;
    sendingTime?:Date

    constructor(json?: Message
    ) {
        if (json != null) {
            this.id = json.id;
            this.role = json.role;
            this.content = json.content;
            this.conversation_id = json.conversation_id;
            this.sendingTime = json.sendingTime;
        }
    }
}
