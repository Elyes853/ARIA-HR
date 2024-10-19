import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { OfferComponent } from './offer.component';

@NgModule({
	imports: [RouterModule.forChild([
		{ path: '', component: OfferComponent }
	])],
	exports: [RouterModule]
})
export class OfferRoutingModule { }
