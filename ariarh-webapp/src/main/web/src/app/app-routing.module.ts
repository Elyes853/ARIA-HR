// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes, CanActivate } from '@angular/router';
import { NotfoundComponent } from './demo/components/notfound/notfound.component';
import { AppLayoutComponent } from "./layout/app.layout.component";
import { AcceuilComponent } from "./demo/components/home/acceuil/acceuil.component";
import { UserRegistrationComponent } from "./demo/components/user-registration/user-registration.component";
import { AuthGuard } from './auth.guard';
import {AuthentificationService} from "./demo/service/authentification.service";
import {ConversationComponent} from "./demo/components/conversation/conversation.component";
import {ThankYouComponent} from "./demo/components/thank-you/thank-you.component";
import {CandidatureComponent} from "./demo/components/candidature/candidature.component";
import {OfferCandidatureComponent} from "./demo/components/offer-candidature/offer-candidature.component";
import {CandidateProfileComponent} from "./demo/components/candidate-profile/candidate-profile.component";
import {ShowConversationComponent} from "./demo/components/show-conversation/show-conversation.component";

const routes: Routes = [
    {
        path: '', component: AppLayoutComponent,
        children: [
            { path: '', component: AcceuilComponent },
            { path: 'candidate/:candidateId', component: CandidateProfileComponent },
            { path: 'candidate/conversation/:candidateId', component: ShowConversationComponent },
            {path: 'conversation', component: ConversationComponent},
            {path: 'merci', component: ThankYouComponent},
            {path: 'candidature', component: CandidatureComponent},
            {path: 'candidature/:id', component: OfferCandidatureComponent},
            { path: 'inscription/:id/:cvId', component: UserRegistrationComponent },
            { path: 'home', loadChildren: () => import('./demo/components/home/home.module').then(m => m.HomeModule)  },
            { path: 'uikit', loadChildren: () => import('./demo/components/uikit/uikit.module').then(m => m.UIkitModule)  ,canActivate: [AuthGuard],},
            { path: 'utilities', loadChildren: () => import('./demo/components/utilities/utilities.module').then(m => m.UtilitiesModule) },
            { path: 'documentation', loadChildren: () => import('./demo/components/documentation/documentation.module').then(m => m.DocumentationModule) },
            { path: 'blocks', loadChildren: () => import('./demo/components/primeblocks/primeblocks.module').then(m => m.PrimeBlocksModule) },
            { path: 'pages', loadChildren: () => import('./demo/components/pages/pages.module').then(m => m.PagesModule) ,  canActivate: [AuthGuard]},

        ]
    },
    { path: 'auth', loadChildren: () => import('./demo/components/auth/auth.module').then(m => m.AuthModule) },
    { path: 'landing', loadChildren: () => import('./demo/components/landing/landing.module').then(m => m.LandingModule) },
    { path: 'notfound', component: NotfoundComponent },
    { path: '**', redirectTo: '/notfound' },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
    providers: [AuthGuard, AuthentificationService] // Provide AuthGuard and AuthentificationService
})
export class AppRoutingModule { }
