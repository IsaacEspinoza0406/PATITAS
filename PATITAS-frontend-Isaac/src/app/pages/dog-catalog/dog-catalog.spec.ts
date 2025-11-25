import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DogCatalog } from './dog-catalog';

describe('DogCatalog', () => {
  let component: DogCatalog;
  let fixture: ComponentFixture<DogCatalog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DogCatalog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DogCatalog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
