import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {

    model: any[] = [];

    constructor(public layoutService: LayoutService) { }

    ngOnInit() {
        this.model = [
            {
                label: 'Home',
                items: [
                    { label: 'Accueil', icon: 'pi pi-fw pi-home', routerLink: ['/'] },
                    { label: 'Offres', icon: 'pi pi-fw pi-briefcase', routerLink: ['/home/offres'] },
                    {
                        label: 'Auth',
                        icon: 'pi pi-fw pi-user',
                        items: [
                            {
                                label: 'Login',
                                icon: 'pi pi-fw pi-sign-in',
                                routerLink: ['/auth/login']
                            },
                            {
                                label: 'Error',
                                icon: 'pi pi-fw pi-times-circle',
                                routerLink: ['/auth/error']
                            },
                            {
                                label: 'Access Denied',
                                icon: 'pi pi-fw pi-lock',
                                routerLink: ['/auth/access']
                            }
                        ]
                    },
                ]
            },

            {
                label: 'Admin',
                icon: 'pi pi-fw pi-briefcase',
                items: [
                    {
                        label: 'Candidatures',
                        icon: 'pi pi-fw pi-id-card',
                        routerLink: ['/candidature']
                    },

                    {
                        label: 'Crud',
                        icon: 'pi pi-fw pi-pencil',
                        routerLink: ['/pages/crud']
                    },
                    {
                        label: 'Créer Offre',
                        icon: 'pi pi-fw pi-pencil',
                        routerLink: ['/pages/create/offer']
                    },
                    {
                        label: 'Tests Existants',
                        icon: 'pi pi-fw pi-book',
                        routerLink: ['/pages/test']
                    },
                    {
                        label: 'Questions Existants',
                        icon: 'pi pi-fw pi-question',
                        routerLink: ['/pages/questions']
                    },
                    {
                        label: 'Créer Test',
                        icon: 'pi pi-fw pi-file-edit',
                        routerLink: ['/pages/test/create']
                    },
                    {
                        label: 'Créer Question',
                        icon: 'pi pi-fw pi-question-circle',
                        routerLink: ['/pages/question/create']
                    },
                ]
            },
        ];
    }
}
