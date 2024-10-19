import {CV} from "./CV";

export class Candidate {
    id?: number;
    name?: string;
    email?: string;
    location?: string;
    phone?: string;
    role?: string;
    cv?: CV;
    score?: CV;
    resume?: string;


    constructor(json?:Candidate
    ) {
        if (json != null) {
            this.id = json.id;
            this.name = json.name;
            this.email = json.email;
            this.location = json.location;
            this.phone = json.phone;
            this.role = json.role;
            this.cv = json.cv;
            this.score = json.score;
            this.resume = json.resume;
        }
    }
}
