import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowConversationComponent } from './show-conversation.component';

describe('ShowConversationComponent', () => {
  let component: ShowConversationComponent;
  let fixture: ComponentFixture<ShowConversationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShowConversationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ShowConversationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
