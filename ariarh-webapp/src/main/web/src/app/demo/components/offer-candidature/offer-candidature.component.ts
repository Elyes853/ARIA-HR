import {Component, OnInit} from '@angular/core';
import {Offer} from "../../api/offer";
import {OfferService} from "../../service/offer.service";
import {MessageService, SortEvent} from "primeng/api";
import {ActivatedRoute, Router} from "@angular/router";
import {Table, TableModule} from "primeng/table";
import {ToastModule} from "primeng/toast";
import {Candidate} from "../../model/Candidat";
import {CandidateService} from "../../service/candidate.service";
import {ButtonModule} from "primeng/button";
import {InputTextModule} from "primeng/inputtext";
import {RippleModule} from "primeng/ripple";
import {FileUploadModule} from "primeng/fileupload";
import {FormsModule} from "@angular/forms";
import {ToolbarModule} from "primeng/toolbar";
import {RatingModule} from "primeng/rating";
import {InputTextareaModule} from "primeng/inputtextarea";
import {DropdownModule} from "primeng/dropdown";
import {RadioButtonModule} from "primeng/radiobutton";
import {InputNumberModule} from "primeng/inputnumber";
import {DialogModule} from "primeng/dialog";
import {NgClass, NgIf} from "@angular/common";
import {QuillEditorComponent} from "ngx-quill";

@Component({
  selector: 'app-offre-candidature',
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
  templateUrl: './offer-candidature.component.html',
  styleUrl: './offer-candidature.component.scss',
    providers:[MessageService]
})
export class OfferCandidatureComponent implements OnInit{
    testString:string = ``

    offerDialog: boolean = false;

    selectedOffers: Offer[] = [];

    submitted: boolean = false;

    isSorted: boolean = false;

    cols: any[] = [];

    statuses: any[] = [];

    offersList  : Offer[] = [];

    offer : Offer = {}

    offerId!: number

    rowsPerPageOptions = [5, 10, 20];

    candidatesList !: Candidate[]

    candidate!:Candidate

    constructor(private offerService: OfferService, private route: ActivatedRoute, private candidateService: CandidateService, private messageService: MessageService, private router:Router) { }

    ngOnInit() {

        this.route.params.subscribe(params => {
            this.offerId = params['id'];
            console.log(this.offerId)

            this.candidateService.getCandidatesPerOffer(this.offerId)
                .subscribe(
                    candidateList=>{
                        console.log(candidateList)
                        this.candidatesList = candidateList

                    }
                )

            this.offerService.getOfferById(this.offerId)
                .subscribe(
                    offer=>this.offer = offer
                )

        });

    }




    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }



    viewCandidate(candidate: Candidate) {
        // Navigate to the offer detail route with the offer ID as a parameter
        this.router.navigate(['/candidate/' , candidate.id]);
    }


}
