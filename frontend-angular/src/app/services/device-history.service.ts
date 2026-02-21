import { HttpClient } from '@angular/common/http';
import { Injectable,inject} from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class DeviceHistoryService {
  http=inject(HttpClient)
  router=inject(Router)

  constructor() { }

  devicelogin(values:any){
    return this.http.post(environment.apiUrl+"/device/login",values)

  }

  deviceCount(userId:any){
    return this.http.get(environment.apiUrl+"/device/deviceCount/"+userId)
  }

  getAllDevices(){
    return this.http.get(environment.apiUrl+"/device/all/devices")
  }

   deleteDeviceById(deviceId:any){
    return this.http.delete(environment.apiUrl+"/device/delete/"+deviceId)
  }

}
