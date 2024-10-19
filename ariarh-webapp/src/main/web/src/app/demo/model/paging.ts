
import {LazyLoadEvent} from 'primeng/api';

export class PageResponse<E> {
    constructor(public totalPages: number,
                public totalElements: number,
                public content : E[]) {	}

    // remove the passed element from the content array.
    remove(element : E) {
        let indexToRemove : number = this.content.indexOf(element);
        this.content = this.content.filter((val,i) => i!=indexToRemove);
        this.totalElements = this.totalElements - 1;
    }
}

export class PageRequestByCriteria<E> {
    constructor(public criteria : E,
                public lazyLoadEvent : LazyLoadEvent) { }
}
