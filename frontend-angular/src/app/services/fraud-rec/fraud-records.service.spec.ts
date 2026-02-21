import { TestBed } from '@angular/core/testing';

import { FraudRecordsService } from './fraud-records.service';

describe('FraudRecordsService', () => {
  let service: FraudRecordsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FraudRecordsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
