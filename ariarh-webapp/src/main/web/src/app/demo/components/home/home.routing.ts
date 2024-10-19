import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {UploadCvComponent} from "./upload-cv/upload-cv.component";

@NgModule({
    imports: [RouterModule.forChild([
        {path:'offres', loadChildren: () => import('./display-offer/display-offer.module').then(m => m.DisplayOfferModule) },
        {path:'upload/:id',component:UploadCvComponent },
        { path: '**', redirectTo: '/notfound' }

    ])],
    exports: [RouterModule]
})
export class HomeRoutingModule { }
