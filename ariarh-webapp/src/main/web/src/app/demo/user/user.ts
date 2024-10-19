export class User {
    private _id: number | undefined;
    private _name: string | undefined;
    private _email: string | undefined;
    private _phone: string | undefined;
    private _address: string | undefined;
    private _role: string | undefined;



    constructor(id?: number, name?: string, email?: string, phone?: string, location?: string, role?: string) {
        this._id = id;
        this._name = name;
        this._email = email;
        this._phone = phone;
        this._address = location;
        this._role = role;
    }


    get id(): number {
        return <number>this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get name(): string | undefined {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get email(): string | undefined{
        return this._email;
    }

    set email(value: string) {
        this._email = value;
    }

    get phone(): string | undefined{
        return this._phone;
    }

    set phone(value: string) {
        this._phone = value;
    }

    get location(): string | undefined{
        return this._address;
    }

    set location(value: string) {
        this._address = value;
    }

    get role(): string | undefined{
        return this._role;
    }

    set role(value: string) {
        this._role = value;
    }
}
