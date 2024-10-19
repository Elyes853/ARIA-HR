import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {User} from "../../model/User";
import {CommonModule} from "@angular/common";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {ActivatedRoute, Router, RouterLink, RouterModule} from "@angular/router";
import {ChipsModule} from "primeng/chips";
import {PaginatorModule} from "primeng/paginator";
import {AutoCompleteModule} from "primeng/autocomplete";
import {UserService} from "../../service/user/user.service";
import {HttpClient} from "@angular/common/http";
import {AuthentificationService} from "../../service/authentification.service";
import {Candidate} from "../../model/Candidat";
import {CV} from "../../model/CV";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";

@Component({
    selector: 'app-user-registration',
    standalone: true,
    imports: [CommonModule, FormsModule, ButtonModule, RouterLink, ChipsModule, PaginatorModule, AutoCompleteModule, ReactiveFormsModule, ToastModule],
    templateUrl: './user-registration.component.html',
    styleUrl: './user-registration.component.scss',
    providers:[MessageService]
})
export class UserRegistrationComponent implements OnInit{
    constructor(private messageService:MessageService, private userService: UserService, private cdr: ChangeDetectorRef ,  private http: HttpClient , private authService:AuthentificationService , private route : ActivatedRoute, private router:Router ) {
    }

    user: User | null | undefined;
    usernameValue: string | undefined
    emailValue: string | undefined
    locationValue: string | undefined
    phoneValue: string | undefined
    cv:CV|undefined
    private id!: number;
    private offerId!: number;
    isLoading = false;




    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.offerId = params['id'];
            this.id = params['cvId'];
            console.log(this.offerId, this.id)
        });

        this.userService.currentUser.subscribe(user => {
            console.log(user)
            this.user = user
            if (this.user){
                console.log(this.user.nom);
                this.usernameValue = this.user.nom;
                this.emailValue = this.user.email;
                this.phoneValue = this.user.telephone;
                this.locationValue = this.user.adresse;
            }else{
                console.log("no user ")
            }
        });
    }

    submit(): void {
        this.isLoading = true
        const headers = this.authService.getAuthorizationHeader();

        let user = new Candidate();
        let cv = new CV({"id":this.id});

        user.name = this.usernameValue ?? '';
        user.email = this.emailValue ?? '';
        user.phone = this.phoneValue ?? '';
        user.location = this.locationValue ?? '';
        user.role =  'candidat';
        user.cv = cv;

        console.log(user,this.offerId)


        this.messageService.add({severity: 'info', summary: 'Nous allons Vous rediriger', detail: 'Veuillez patienter'});
        this.userService.create(user,this.offerId).subscribe(
            () => {
                this.isLoading = false;

                this.router.navigate(["/merci"]
                )
            }
        );
    }

}
