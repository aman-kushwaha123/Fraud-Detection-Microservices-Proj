import { HttpClient } from '@angular/common/http';
import { Injectable,inject } from '@angular/core';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
   http=inject(HttpClient)

  constructor() { }

  getAllAccounts(userId:number){
     return this.http.get(environment.apiUrl+"/Account/allAccounts/"+userId)
  }
  addAccount(values:object){
    return this.http.post(environment.apiUrl+"/Account/add",values)

  }
  allAccounts(){
    return this.http.get(environment.apiUrl+"/Account/allAccounts")
  }

  deleteAccountById(accountId:any){
    return this.http.delete(environment.apiUrl+"/Account/delete/"+accountId)
  }
}
