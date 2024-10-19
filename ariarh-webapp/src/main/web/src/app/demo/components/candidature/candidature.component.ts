import {Component, OnInit} from '@angular/core';
import {FileUploadModule} from "primeng/fileupload";
import {NgIf} from "@angular/common";
import {MessageService, SharedModule} from "primeng/api";
import {Offer} from "../../api/offer";
import {OfferService} from "../../service/offer.service";
import {Router} from "@angular/router";
import {Table, TableModule} from "primeng/table";
import {InputTextModule} from "primeng/inputtext";
import {RippleModule} from "primeng/ripple";
import {ToastModule} from "primeng/toast";

@Component({
  selector: 'app-candidature',
  standalone: true,
    imports: [
        FileUploadModule,
        NgIf,
        SharedModule,
        InputTextModule,
        RippleModule,
        TableModule,
        ToastModule
    ],
  templateUrl: './candidature.component.html',
  styleUrl: './candidature.component.scss',
    providers:[MessageService]
})
export class CandidatureComponent implements OnInit{

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
            console.log(this.offersList)
        })
    }

    viewOffer(offer: any) {
        // Navigate to the offer detail route with the offer ID as a parameter
        this.router.navigate(['/candidature/' , offer.id]);
    }



}
