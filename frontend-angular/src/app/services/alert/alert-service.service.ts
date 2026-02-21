import { HttpClient } from '@angular/common/http';
import { Injectable,inject } from '@angular/core';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AlertServiceService {
  http=inject(HttpClient)
  constructor() { }

  getAllAlerts(){
    return this.http.get(environment.apiUrl+"/alert/all/alerts")
  }

  deletAlertById(alertId:any){
    return this.http.delete(environment.apiUrl+"/alert/delete/"+alertId)
  }
}
