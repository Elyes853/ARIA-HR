import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {DisplayOfferComponent} from "./display-offer.component";
@NgModule({
    imports: [RouterModule.forChild([
        {path:'', component:DisplayOfferComponent}
    ])],
    exports: [RouterModule]
})
export class DisplayOfferRoutingModule {

}


