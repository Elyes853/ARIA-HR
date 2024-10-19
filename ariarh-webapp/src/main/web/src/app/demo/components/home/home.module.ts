import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HomeRoutingModule} from "./home.routing";
import {UploadCvComponent} from "./upload-cv/upload-cv.component";



@NgModule({
  declarations: [],
  imports: [
      CommonModule,
      UploadCvComponent,
      HomeRoutingModule,
  ]
})
export class HomeModule { }
