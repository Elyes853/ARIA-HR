import { User } from './User';
import { Permission } from './Permission';

export class Profil {
    id?: number;
    codeMetier?: string;
    description?: string;
    permissions?: Permission[];
    user?: User[];

    constructor(json?: any) {
      if (json != null) {
        this.id = json.id;
        this.codeMetier = json.codeMetier;
        this.description = json.description;
        this.permissions = json.permissions;
        this.user = json.user;
      }
    }
}


