import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {CreateTestComponent} from "./create-test/create-test.component";
import {TestComponent} from "./test/test.component";
import {CreateQuestionComponent} from "./create-question/create-question.component";
import {QuestionsComponent} from "./questions/questions.component";

@NgModule({
    imports: [RouterModule.forChild([
        { path:"create/offer", loadChildren: () => import('./offer/offer.module').then(m => m.OfferModule) },
        { path:"test",component:TestComponent},
        { path:"questions",component:QuestionsComponent},
        { path:"test/create",component:CreateTestComponent},
        { path:"question/create",component:CreateQuestionComponent},
        { path: 'crud', loadChildren: () => import('./crud/crud.module').then(m => m.CrudModule) },
        { path: 'empty', loadChildren: () => import('./empty/emptydemo.module').then(m => m.EmptyDemoModule) },
        { path: 'timeline', loadChildren: () => import('./timeline/timelinedemo.module').then(m => m.TimelineDemoModule) },
        { path: '**', redirectTo: '/notfound' }
    ])],
    exports: [RouterModule]
})
export class PagesRoutingModule { }
