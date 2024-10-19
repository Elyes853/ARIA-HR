export class CV {
    id!: number;

    constructor(json?: CV
    ) {
        if (json != null) {
            this.id = json.id;
        }
    }
}
