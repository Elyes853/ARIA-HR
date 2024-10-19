
import { Profil } from "./Profil";

export class User {
    id?: number;
    nom!: string;
    prenom!: string;
    email!: string;
    password!: string;
    adresse!: string;
    dateNaissance!: Date;
    telephone!: string;
    activated!: boolean;
    nonLocked!: boolean;
    profile!:Profil;
    authorities?: any[];
    pictureLink!: string;
    createdBy!: string;
    createdDate?: Date;
    lastModifiedBy?: string;
    lastModifiedDate?: Date;
    cvId ?: string;
    offerId?:string;

  constructor(json?: User ) {
    if (json != null) {
        this.id = json.id;
        this.nom = json.nom;
        this.prenom = json.prenom;
        this.password = json.password;
        this.dateNaissance = json.dateNaissance;
        this.telephone = json.telephone;
        this.activated = json.activated;
        this.email = json.email;
        this.nonLocked = json.nonLocked;
        this.pictureLink = json.pictureLink;
        this.profile = json.profile;
        this.authorities = json.authorities;
        this.createdBy = json.createdBy;
    }
  }
}
