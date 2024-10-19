import { Component } from '@angular/core';
import {ButtonModule} from "primeng/button";
import {RouterModule} from "@angular/router";

@Component({
  selector: 'app-acceuil',
  standalone: true,
  imports: [
    ButtonModule,
    RouterModule,
  ],
  templateUrl: './acceuil.component.html',
  styleUrl: './acceuil.component.scss'
})
export class AcceuilComponent {

}
