import {Component, OnInit} from '@angular/core';
import {CandidateService} from "../../service/candidate.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Candidate} from "../../model/Candidat";
import {ButtonModule} from "primeng/button";
import {NgIf} from "@angular/common";
import {MarkdownModule, MarkdownPipe} from "ngx-markdown";
import { saveAs } from 'file-saver';




@Component({
  selector: 'app-candidate-profile',
  standalone: true,
    imports: [
        ButtonModule,
        NgIf,
        MarkdownModule,
        MarkdownPipe
    ],
  templateUrl: './candidate-profile.component.html',
  styleUrl: './candidate-profile.component.scss',


})
export class CandidateProfileComponent implements OnInit{

    candidate !: Candidate



    constructor(private route: ActivatedRoute, private candidateService: CandidateService, private router:Router){}

    ngOnInit() {
        this.candidate = new Candidate()
        const candidateId : string|null  = this.route.snapshot.paramMap.get('candidateId')
        if(candidateId) {
            this.candidateService.getCandidateById(+candidateId)
                .subscribe(candidate=>{
                    this.candidate = candidate
                    console.log(this.candidate)
                    console.log(candidate)
                })
        }
    }

    showCV() {
        const candidateId = this.candidate.id!;
        this.candidateService.getCandidateCV(candidateId)
            .subscribe((blob: Blob) => {
/*
                saveAs(blob, `candidate_${candidateId}_cv.pdf`);  // Use the file-saver library to save the file
*/
                const fileURL = URL.createObjectURL(blob);
                window.open(fileURL, '_blank');


            });
}

    showConversation(){
        // Navigate to the offer detail route with the offer ID as a parameter
        this.router.navigate(['/candidate/conversation/' , this.candidate.id]);
    }

}
