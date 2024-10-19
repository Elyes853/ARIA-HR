import {Component, OnInit} from '@angular/core';
import {QuillEditorComponent, QuillModule} from "ngx-quill";
import {Table, TableModule} from "primeng/table";
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
import {Offer} from "../../../api/offer";
import {OfferService} from "../../../service/offer.service";
import {MessageService} from "primeng/api";
import {NgClass, NgIf} from "@angular/common";
import {Router} from "@angular/router";


@Component({
  selector: 'app-display-offer',
  standalone: true,
    imports: [
        TableModule,
        FileUploadModule,
        FormsModule,
        ButtonModule,
        RippleModule,
        ToastModule,
        ToolbarModule,
        RatingModule,
        InputTextModule,
        InputTextareaModule,
        DropdownModule,
        RadioButtonModule,
        InputNumberModule,
        DialogModule,
        NgIf,
        QuillEditorComponent,
        NgClass,
    ],
  templateUrl: './display-offer.component.html',
  styleUrl: './display-offer.component.scss',
    providers:[MessageService]
})
export class DisplayOfferComponent implements OnInit{
    testString:string = ``

    offerDialog: boolean = false;

    selectedOffers: Offer[] = [];

    submitted: boolean = false;

    cols: any[] = [];

    statuses: any[] = [];

    offersList  : Offer[] = [];

    offer : Offer = {}

    rowsPerPageOptions = [5, 10, 20];

    constructor(private offerservice:OfferService, private messageService: MessageService, private router:Router) { }

    ngOnInit() {
        this.getOffers()
        console.log(this.offersList)

    }

    openNew() {
        this.offer = {};
        this.submitted = false;
        this.offerDialog = true;
    }


    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    getOffers() {
        this.offerservice.getOffers().subscribe(offers=>{

            this.offersList = offers;
        })
    }

    viewOffer(offer: any) {
        // Navigate to the offer detail route with the offer ID as a parameter
        this.router.navigate(['/home/upload/' , offer.id]);
    }

    getTruncatedText(text: string) {
        const maxLength = 100; // Adjust this value according to your needs
        return text.length > maxLength ? `${text.substring(0, maxLength)}...` : text;
    }




}
