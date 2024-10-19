import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DisplayOfferRoutingModule} from "./display-offer-routing.module";
import {DisplayOfferComponent} from "./display-offer.component";
import {QuillModule} from "ngx-quill";
import {OfferRoutingModule} from "../../pages/offer/offer-routing.module";
import {TableModule} from "primeng/table";
import {FileUploadModule} from "primeng/fileupload";
import {FormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {RippleModule} from "primeng/ripple";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {RatingModule} from "primeng/rating";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {DropdownModule} from "primeng/dropdown";
import {RadioButtonModule} from "primeng/radiobutton";
import {InputNumberModule} from "primeng/inputnumber";
import {DialogModule} from "primeng/dialog";


@NgModule({
  declarations: [],
    imports: [
        CommonModule,
        DisplayOfferRoutingModule,
        DisplayOfferComponent,

    ]
})
export class DisplayOfferModule {}
