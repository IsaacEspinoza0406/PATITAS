import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddPublications } from './add-publications';

describe('AddPublications', () => {
  let component: AddPublications;
  let fixture: ComponentFixture<AddPublications>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddPublications]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddPublications);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
