import {Component, OnInit} from '@angular/core';
import { LayoutService } from 'src/app/layout/service/app.layout.service';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {User} from "../../../model/User";
import {AuthentificationService} from "../../../service/authentification.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styles: [`
        :host ::ng-deep .pi-eye,
        :host ::ng-deep .pi-eye-slash {
            transform:scale(1.6);
            margin-right: 1rem;
            color: var(--primary-color) !important;
        }
    `]
})
export class LoginComponent implements OnInit{

    valCheck: string[] = ['remember'];

    password!: string;
    seConnecterForm!: FormGroup;
    showErrorMessage!: boolean;
    currentUser!: User;
    message!: string;

    constructor(public layoutService: LayoutService , private authentificationService: AuthentificationService , private router: Router) {
        if (localStorage.getItem('currentUser') ) {
            this.router.navigateByUrl('');
        }
    }
    ngOnInit() {
        this.seConnecterForm = new FormGroup({
            email: new FormControl(null, Validators.compose([Validators.required, Validators.email])),
            motDePasse: new FormControl(null, Validators.compose([Validators.required, Validators.minLength(5)]))
        });
        this.showErrorMessage = false;
    }

    signIn() {
        console.log(this.seConnecterForm.get('email'));
        console.log(this.seConnecterForm.get('motDePasse'));
        if (!this.seConnecterForm || this.seConnecterForm.invalid) {
            this.showErrorMessage = true;
            return;
        } else {
            this.authentificationService.login(this.seConnecterForm.get('email')!.value, this.seConnecterForm.get('motDePasse')!.value).subscribe(res =>{
                localStorage.removeItem('typeAuth');
                localStorage.setItem('currentUser', JSON.stringify((res.body)));
                localStorage.setItem('token', res.headers.get('Authorization')!);
                this.currentUser = JSON.parse(localStorage.getItem('currentUser')!);

                this.router.navigateByUrl('');
            },(error)=>{
                console.log(error.headers.get('X-validation-error'));
                let errorHeader = error.headers.get('X-validation-error');
                switch (errorHeader) {
                    case "error.badCredentials":
                        this.showErrorMessage = true;
                        this.message = "L'adresse e-mail ou le mot de passe n'est pas valide";
                        break;
                    case "error.userIsDisabled":
                        this.showErrorMessage = true;
                        this.message = "L'utilisateur est désactivé";
                        break;
                    case "error.userIsLocked":
                        this.showErrorMessage = true;
                        this.message = "L'utilisateur est verrouillé";
                        break;
                    default:
                        this.showErrorMessage = true;
                        this.message = "Vous ne pouvez pas vous identifier";
                        break;
                }
            });
        }

        return;

    }
}
