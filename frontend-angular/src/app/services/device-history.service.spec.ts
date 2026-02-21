import { TestBed } from '@angular/core/testing';

import { DeviceHistoryService } from './device-history.service';

describe('DeviceHistoryService', () => {
  let service: DeviceHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
