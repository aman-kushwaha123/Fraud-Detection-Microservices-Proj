import { HttpClient } from '@angular/common/http';
import { Injectable,inject } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { UserService } from '../user/user.service';

@Injectable({
  providedIn: 'root'
})
export class FraudRecordsService {
   http=inject(HttpClient)
  constructor(private userService:UserService) { }

  getAllFraudRecords(){
    return this.http.get(environment.apiUrl+"/FraudRec/getall")
  }

   deleteFraudRecById(caseId:any){
    return this.http.delete(environment.apiUrl+"/FraudRec/delete/"+caseId)
  }

  updateStatus(caseId:any,username:any){
    return this.http.patch(environment.apiUrl+"/FraudRec/update/status",{
      caseId:caseId,
      username:username
    })
  }
}
