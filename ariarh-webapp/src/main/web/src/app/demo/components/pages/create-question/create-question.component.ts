import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Question} from "../../../api/question";
import {CommonModule} from "@angular/common";
import {QuillEditorComponent, QuillModule} from "ngx-quill";
import {ButtonModule} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";
import {DropdownChangeEvent, DropdownModule} from "primeng/dropdown";
import {Category} from "../../../api/category";
import {QuestionService} from "../../../service/question.service";
import {CategoryService} from "../../../service/category.service";
import {MessageService} from "primeng/api";
import {concatMap, of} from "rxjs";
import {map} from "rxjs/operators";
import {ToastModule} from "primeng/toast";

@Component({
  selector: 'app-create-question',
  standalone: true,
    imports: [
        CommonModule,
        QuillEditorComponent,
        QuillModule,
        FormsModule,
        ButtonModule,
        InputTextModule,
        DropdownModule,
        ToastModule
    ],
  templateUrl: './create-question.component.html',
  styleUrl: './create-question.component.scss',
    providers:[MessageService],
})
export class CreateQuestionComponent implements OnInit{

    categoriesLabelsList: (string | undefined)[] | undefined

    question: Question = new Question()

    category: Category = new Category()

    categoriesList : Category[] = [] ;

    isClicked:boolean = false;

    newCategory:string = ""

    chosenCategory : string = ""

    constructor(private questionService:QuestionService, private categoryService: CategoryService, private messageService: MessageService) {
    }

        ngOnInit() {
            this.categoryService.getCategories().subscribe(categoryList=>{
                console.log(categoryList)
                this.categoriesList = categoryList
                this.categoriesLabelsList = categoryList.map(category => category.label);
                this.categoriesLabelsList.push('Autre')
                console.log(this.categoriesLabelsList)
            })
            console.log(this.categoriesList)


        }


    onItemChange(event:DropdownChangeEvent) {
        if (event.value === "Autre"){
            this.isClicked = true
        }else{
            this.isClicked = false
            let category = this.categoriesList.find(cat => cat.label === this.chosenCategory);
            this.question.category_id = category?.id
        }

    }
    saveQuestionAndCategory() {
        console.log(this.newCategory+" "+this.categoriesLabelsList?.includes(this.newCategory))
        if (this.categoriesLabelsList?.includes(this.newCategory) === false) {
            this.category.label = this.newCategory
            this
            of(this.category).pipe(
                concatMap(category => this.categoryService.addCategory(category)),
                concatMap(addedCategory => {
                    console.log("Catégorie ajoutée");
                    return this.categoryService.getCategoryId(this.newCategory);
                }),
                map(result1 => {
                    console.log(result1)
                    return result1.id
                }),
                concatMap(categoryId => {
                    console.log("Category ID:", categoryId);
                    this.question.category_id = categoryId;
                    return this.questionService.addQuestion(this.question);
                })
            ).subscribe({
                next: questionResponse => {
                    console.log("questionResponse");
                    console.log(questionResponse);
                    this.messageService.add({severity: 'success', summary: 'Succès', detail: 'Question et categorie créées', life: 3000});
                    this.categoriesLabelsList?.splice(this.categoriesLabelsList?.length-1, 0, this.newCategory)
                },
                error: error => {
                    console.log(error);
                    this.messageService.add({severity: 'error', summary: 'erreur', detail: 'Une erreur est survenue', life: 3000});
                }
            });
        }else {
            console.log('bloc else executé')
            console.log(this.question.question)
            console.log(this.question.category_id)
            of(this.chosenCategory).pipe(

                concatMap(addedCategory => {
                    console.log(this.question.question)
                    console.log(this.question.category_id)
                    return this.categoryService.getCategoryId(this.chosenCategory);
                }),
                map(result1 => {
                    console.log(result1)
                    return result1.id
                }),
                concatMap(categoryId => {
                    this.question.category_id = categoryId
                    return this.questionService.addQuestion(this.question)
                }),
            ).subscribe({
                next: questionResponse => {
                    console.log("questionResponse");
                    console.log(questionResponse);
                    this.messageService.add({severity: 'success', summary: 'Succès', detail: 'Question créée', life: 3000});
                },
                error: error => {
                    console.log(error);
                    this.messageService.add({severity: 'error', summary: 'erreur', detail: 'Une erreur est survenue', life: 3000});
                }
            });

        }
    }
}
