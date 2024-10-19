import {Component, OnInit} from '@angular/core';
import {CategoryService} from "../../../service/category.service";
import {Category} from "../../../api/category";
import {AccordionModule} from "primeng/accordion";
import {CheckboxModule} from "primeng/checkbox";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {Question} from "../../../api/question";
import {Test} from "../../../api/test";
import {TestService} from "../../../service/test.service";
import { InputTextModule } from 'primeng/inputtext';
import {ButtonModule} from "primeng/button";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";


@Component({
  selector: 'app-create-test',
  standalone: true,
    imports: [
        CommonModule,
        AccordionModule,
        CheckboxModule,
        FormsModule,
        InputTextModule,
        ButtonModule,
        ToastModule
    ],
  templateUrl: './create-test.component.html',
  styleUrl: './create-test.component.scss',
    providers:[MessageService]

})
export class CreateTestComponent implements OnInit{

    categoriesAnsQuestionsList : Category[] = []

    test: Test = new Test()

    selectedQuestions: Question[] = []


    constructor(private messageService:MessageService, private categoryService: CategoryService, private testService : TestService ) {
    }
    ngOnInit(): void {
        this.categoryService.getCategories().subscribe(
            response=>{
                this.categoriesAnsQuestionsList = response
                console.log(this.categoriesAnsQuestionsList)
            }
        )
    }

    addTest(){
        this.test.questionsList = this.selectedQuestions
        console.log(this.test.nature)
        console.log(this.test.questionsList)
        this.testService.addTest(this.test).subscribe(
            {next:testResponse=> {
                    console.log(testResponse);
                    this.messageService.add({severity: 'success', summary: 'Succès', detail: 'Test créé', life: 3000});
                },error:error=>{
                    this.messageService.add({severity: 'error', summary: 'Erreur', detail: 'Une erreur est parvenue', life: 3000});
                    }
            }
        )
    }

}
