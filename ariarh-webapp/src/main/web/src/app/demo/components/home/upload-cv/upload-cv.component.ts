import {Component, OnInit} from '@angular/core';
import {FileUploadHandlerEvent, FileUploadModule} from 'primeng/fileupload';
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {CommonModule} from "@angular/common";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../service/user/user.service";
import {User} from "../../../model/User";
import {Offer} from "../../../api/offer";
import {OfferService} from "../../../service/offer.service";

interface UserInfo {
    NAME: string;
    PHONE: string;
    EMAIL: string;
    ADDRESS: string;
    cvId : string;
}
interface UploadResponse {
    message: string;
    data:UserInfo;
}
@Component({
    selector: 'app-upload-cv',
    standalone: true,
    imports: [CommonModule, FileUploadModule, ToastModule],
    templateUrl: './upload-cv.component.html',
    styleUrl: './upload-cv.component.scss',
    providers:[MessageService]

})
export class UploadCvComponent  implements  OnInit {

    file:File|undefined;
    offer!: Offer;
    id!: number;

    uploaded = false
    constructor(private messageService: MessageService,private http:HttpClient,private userService:UserService, private router:Router , private route:ActivatedRoute, private offerService: OfferService) {}

    ngOnInit(): void {

        this.offer = new Offer()
        // @ts-ignore
        this.id = +this.route.snapshot.paramMap.get('id');
        if (this.id != null) {
            // Call service method to fetch offer by ID
            this.offerService.getOfferById(this.id)
                .subscribe(
                    (offer: Offer) => {
                        console.log(offer)
                        this.offer = offer;
                        // Optionally, you can perform additional actions after fetching the offer
                    }
                );
        }
    }
    uploadFile(event: FileUploadHandlerEvent){

        this.file = event.files[0]
        console.log(this.file.type)
        if (this.file) {
            if (this.file.type === 'image/png' || this.file.type === 'application/pdf' ||this.file.type === 'image/jpg' || this.file.type === 'image/jpeg' || this.file.type === 'image/tiff')  {
                const formData = new FormData();
                formData.append('file', this.file, this.file.name);
                this.uploaded = true
                this.messageService.add({severity: 'info', summary: 'Veuillez patienter', detail: "Anayse de CV en cours"});

                this.http.post<UploadResponse>('http://localhost:9018/ariarh-middleoffice/upload', formData).subscribe(response => {
                    let message = response.message
                    let data = response.data
                    if (message === 'File Uploaded') {
                        this.messageService.add({severity: 'success', summary: 'File uploaded', detail: ''});
                        let user = new User()
                        user.nom = data.NAME
                        user.email = data.EMAIL
                        user.telephone = data.PHONE
                        user.adresse = data.ADDRESS
                        this.userService.updateUser(user)
                        this.userService.currentUser.subscribe(user => {
                        })
                        this.router.navigate(['/inscription',this.id , response.data.cvId])
                    } else {
                        this.messageService.add({severity: 'error', summary: 'Failed to upload', detail: ''});
                    }
                });

            }else{
                this.messageService.add({severity: 'warn', summary: 'Format svg et webp non supportés!'  , detail: ''});
            }
        }
    }
}


