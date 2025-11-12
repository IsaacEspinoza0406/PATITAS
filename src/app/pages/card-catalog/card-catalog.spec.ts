import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardCatalog } from './card-catalog';

describe('CardCatalog', () => {
  let component: CardCatalog;
  let fixture: ComponentFixture<CardCatalog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardCatalog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardCatalog);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
