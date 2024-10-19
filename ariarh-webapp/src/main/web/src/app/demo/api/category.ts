import {Question} from "./question";

export class Category{
    _id?:number;
    label?:string;
    questionList?:Question[]

    constructor(id?:number,label?:string, questionList?:Question[]) {
        this._id = id
        this.label = label
        this.questionList = questionList

    }

    get id():number{
        return this.id
    }
}
