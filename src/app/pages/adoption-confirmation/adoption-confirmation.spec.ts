import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdoptionConfirmationComponent } from './adoption-confirmation';

describe('AdoptionConfirmationComponent', () => {
  let component: AdoptionConfirmationComponent;
  let fixture: ComponentFixture<AdoptionConfirmationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdoptionConfirmationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdoptionConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
