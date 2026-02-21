import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FraudRecordsComponent } from './fraud-records.component';

describe('FraudRecordsComponent', () => {
  let component: FraudRecordsComponent;
  let fixture: ComponentFixture<FraudRecordsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FraudRecordsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FraudRecordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
