import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdoptionRequestForm } from './adoption-request-form';

describe('AdoptionRequestForm', () => {
  let component: AdoptionRequestForm;
  let fixture: ComponentFixture<AdoptionRequestForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdoptionRequestForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdoptionRequestForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
