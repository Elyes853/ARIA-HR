import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayOfferComponent } from './display-offer.component';

describe('DisplayOfferComponent', () => {
  let component: DisplayOfferComponent;
  let fixture: ComponentFixture<DisplayOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DisplayOfferComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DisplayOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
