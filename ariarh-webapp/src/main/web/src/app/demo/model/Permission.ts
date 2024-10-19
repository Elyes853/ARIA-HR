export class Permission{
    id!: number;
    role!: string;
    description!: string;
    grouped!: string;

    constructor(json?: any) {
      if (json != null) {
        this.id = json.id;
        this.role = json.role;
        this.description = json.description;
        this.grouped = json.grouped;
      }
    }
}