import { Injectable } from '@angular/core';
import { CurrentLocation } from '../../types/CurrentLocation';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
   //no imports needed
   //Browser permission popup appears automatically
  constructor() { }
  getCurrentLocation():Observable<CurrentLocation>{
    return new Observable(observer=>{
      //Browser API (no Angular Import needed)
      
    if(!navigator.geolocation){
      alert('GeoLocation not supported')
      return;
    }
    navigator.geolocation.getCurrentPosition(
      position=>{
        observer.next({
          latitude:position.coords.latitude,
          longitude:position.coords.longitude,
          label:'Current Location'
        });
        observer.complete();
      },
      error =>observer.error(error)
    )

    
    
  })
}
}
