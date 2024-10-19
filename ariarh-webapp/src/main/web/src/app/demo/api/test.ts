import {Question} from "./question";

export class Test {
    id: number | undefined;
    nature: string | undefined;
    questionsList?: Question[];

    constructor(id?: number, nature?: string, questionList?: Question[]) {
        this.id = id;
        this.nature = nature;
        this.questionsList = questionList;
    }
}

