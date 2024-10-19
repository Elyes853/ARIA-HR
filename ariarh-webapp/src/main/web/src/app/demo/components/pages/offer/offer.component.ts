import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Table } from 'primeng/table';
import { Offer } from "../../../api/offer";
import { OfferService } from "../../../service/offer.service";
import {Router} from "@angular/router";

@Component({
    templateUrl: './offer.component.html',
    providers: [MessageService]
})
export class OfferComponent implements OnInit {

    testString: string = ``;

    offerDialog: boolean = false;
    deleteOfferDialog: boolean = false;
    deleteOffersDialog: boolean = false;

    selectedOffers: Offer[] = [];
    submitted: boolean = false;


    cols: any[] = [
        { field: 'title', header: 'Offre' },
        { field: 'description', header: 'Description' },
        { field: 'responsibilities', header: 'Responsabilités' },
        { field: 'requiredSkills', header: 'Compétences requises' },
        { field: 'experienceRequired', header: 'Expérience requise' },
        { field: 'education', header: 'Éducation' },
        { field: 'location', header: 'Emplacement' },
        { field: 'contractType', header: 'Type de contrat' },
        { field: 'benefits', header: 'Avantages' },
        { field: 'applicationProcess', header: 'Processus de candidature' },
        { field: 'applicationDeadline', header: 'Date limite de candidature' }
    ];

    offersList: Offer[] = [];
    offer: Offer = {};

    editorOptions = {
        toolbar: [
            ['bold', 'italic', 'underline'], // toggled buttons
            [{ 'header': [1, 2, 3, 4, 5, 6, false] }], // headings
            [{ 'list': 'ordered'}, { 'list': 'bullet' }], // lists
            [{ 'color': [] }], // dropdown with defaults from theme
            [{ 'size': ['small', 'normal', 'large', 'huge'] }] // custom dropdown
        ]
    };

    rowsPerPageOptions = [5, 10, 20];

    constructor(private offerService: OfferService, private messageService: MessageService ,private router: Router) { }

    ngOnInit() {
        this.getOffers();
    }

    openNew() {
        this.offer = {};
        this.submitted = false;
        this.offerDialog = true;
    }

    deleteSelectedProducts() {
        this.deleteOffersDialog = true;
    }

    editProduct(offer: Offer) {
        this.offer = { ...offer };
        this.offerDialog = true;
    }

    deleteProduct(offer: Offer) {
        this.deleteOfferDialog = true;
        this.offer = { ...offer };
    }

    confirmDeleteSelected() {
        this.deleteOffersDialog = false;
        this.selectedOffers.forEach(selectedOffer => {
            this.offersList = this.offersList.filter(offer => offer.id !== selectedOffer.id);
        });
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
        this.selectedOffers = [];
    }

    confirmDelete() {
        this.deleteOfferDialog = false;
        this.offersList = this.offersList.filter(val => val.id !== this.offer.id);
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000 });
        this.offer = {};
    }

    hideDialog() {
        this.offerDialog = false;
        this.submitted = false;
    }

    saveOffer() {
        this.submitted = true;

        if (this.offer.title?.trim() && this.offer.description?.trim()) {
            this.offerService.addOffer(this.offer).subscribe({
                next: (response) => {
                    console.log("success");
                    console.log(response);
                    this.offersList.push(this.offer);
                    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Offre créée', life: 3000 });
                    this.offerDialog = false;
                    this.offer = {};
                },
                error: (error) => {
                    console.log("error");
                    console.log(error);
                    this.messageService.add({ severity: 'error', summary: 'erreur', detail: 'Une erreur est survenue', life: 3000 });
                }
            });
        }
    }

    getTruncatedText(text: string) {
        const maxLength = 100; // Adjust this value according to your needs
        return text.length > maxLength ? `${text.substring(0, maxLength)}...` : text;
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    getOffers() {
        this.offerService.getOffers().subscribe(offers => {
            this.offersList = offers;
        });
    }

    viewOffer(offer: any) {
        // Navigate to the offer detail route with the offer ID as a parameter
        this.router.navigate(['/home/upload/' , offer.id]);
    }
}
