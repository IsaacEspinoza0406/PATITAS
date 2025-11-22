import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPublicationsImg } from './add-publications-img';

describe('AddPublicationsImg', () => {
  let component: AddPublicationsImg;
  let fixture: ComponentFixture<AddPublicationsImg>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddPublicationsImg]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddPublicationsImg);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
