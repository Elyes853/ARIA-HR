export class Question{
    id?:number;
    question?:string;
    category_id?:number;

    constructor(id?: number, question?:string, category?: number) {
        this.id = id;
        this.question = question
        this.category_id = category
    }

}

