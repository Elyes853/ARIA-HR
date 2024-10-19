import {Candidate} from "./Candidat";
import {Message} from "./Message";

export class Conversation {
    id?: number | undefined;
    candidate: Candidate | undefined;
    messagesList?: Message[] | undefined

    constructor(json?: Conversation
    ) {
        if (json != null) {
            this.id = json.id;
            this.candidate = json.candidate;
            this.messagesList = json.messagesList;
        }
    }
}
