import {Candidate} from "../model/Candidat";

export class Offer {
    id?: number;
    title?: string;
    description?: string;
    responsibilities?: string;
    requiredSkills?: string;
    experienceRequired?: string;
    education?: string;
    location?: string;
    contractType?: string;
    benefits?: string;
    applicationProcess?: string;
    applicationDeadline?: Date;
    candidatesList?: Candidate[]


}
