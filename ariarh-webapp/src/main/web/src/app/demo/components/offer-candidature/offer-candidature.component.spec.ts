import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferCandidatureComponent } from './offer-candidature.component';

describe('OffreCandidatureComponent', () => {
  let component: OfferCandidatureComponent;
  let fixture: ComponentFixture<OfferCandidatureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OfferCandidatureComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OfferCandidatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
