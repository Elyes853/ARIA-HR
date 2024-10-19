import { Component } from '@angular/core';
import {Offer} from "../../../api/offer";
import {OfferService} from "../../../service/offer.service";
import {MessageService, SharedModule} from "primeng/api";
import {Table, TableModule} from "primeng/table";
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {InputTextModule} from "primeng/inputtext";
import {NgClass, NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {QuillEditorComponent} from "ngx-quill";
import {RippleModule} from "primeng/ripple";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {Router} from "@angular/router";
import {FileUploadEvent, FileUploadModule} from "primeng/fileupload";

interface UploadEvent {
    originalEvent: Event;
    files: File[];
}

@Component({
    selector: 'app-test',
    standalone: true,
    imports: [
        ButtonModule,
        DialogModule,
        InputTextModule,
        NgIf,
        PaginatorModule,
        QuillEditorComponent,
        RippleModule,
        SharedModule,
        TableModule,
        ToastModule,
        ToolbarModule,
        NgClass,
        FileUploadModule
    ],
    templateUrl: './test.component.html',
    styleUrl: './test.component.scss',
    providers: [MessageService]
})
export class TestComponent {

    uploadedFiles: any[] = [];

    offerDialog: boolean = false;

    deleteOfferDialog: boolean = false;

    deleteOffersDialog: boolean = false;

    selectedOffers: Offer[] = [];

    submitted: boolean = false;

    cols: any[] = [];

    statuses: any[] = [];

    offersList  : Offer[] = [];

    offer : Offer = {}

    rowsPerPageOptions = [5, 10, 20];

    constructor(private router:Router, private offerservice:OfferService, private messageService: MessageService) { }

    ngOnInit() {

        this.getOffers();
        console.log(this.offersList)

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
        this.offersList = this.offersList.filter(val => !this.selectedOffers.includes(val));
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

    saveProduct() {
        this.submitted = true;

        if (this.offer.title?.trim()) {
            if (this.offer.id) {
                // @ts-ignore
                this.offer.inventoryStatus = this.offer.inventoryStatus.value ? this.offer.inventoryStatus.value : this.offer.inventoryStatus;
                // @ts-ignore
                this.offer[this.findIndexById(this.offer.id)] = this.offer;
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
            } else {
                this.offer.id = +this.createId(); // must be replaced with a post request
                this.offersList.push(this.offer);
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000 });
            }

            this.offersList = [...this.offersList];
            this.offerDialog = false;
            this.offer = {};
        }
    }

    findIndexById(id: number): number {
        let index = -1;
        for (let i = 0; i < this.offersList.length; i++) {
            if (this.offersList[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    }

    createId(): string {
        let id = '';
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        for (let i = 0; i < 5; i++) {
            id += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return id;
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    //put it in the ngOninit
    getOffers() {
        this.offerservice.getOffers().subscribe(offers=>{

            this.offersList = offers;
        })
    }
    saveOffer(){
        this.offerservice.addOffer(this.offer).subscribe({next:(response:Offer)=>{
                console.log("success")
                console.log(response)
                this.offersList.push(this.offer);
                this.submitted = true;
                this.messageService.add({ severity: 'Success', summary: 'Succès', detail: 'Offre créée', life: 3000 });
            },
            error: (error:any) => {
                console.log("error")
                console.log(error);
                this.messageService.add({ severity: 'error', summary: 'erreur', detail: 'Une erreur est survenue', life: 3000 });

            }}
        )

        this.offersList = [...this.offersList];
        this.offerDialog = false;
        this.offer = {};
    }



    createNewTest(){
        this.router.navigate(["/newTest"])
    }

    onUpload(event: FileUploadEvent) {
        for(let file of event.files) {
            this.uploadedFiles.push(file);
            this.messageService.add({severity: 'info', summary: 'File Uploaded', detail: ''});

        }

    }

}
