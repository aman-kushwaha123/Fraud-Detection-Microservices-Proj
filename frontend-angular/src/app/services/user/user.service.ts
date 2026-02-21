import { HttpClient } from '@angular/common/http';
import { Injectable,inject } from '@angular/core';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  http=inject(HttpClient)
  constructor() { }

  getAllUsers(){
    return this.http.get(environment.apiUrl+"/userCredential/all/users")
  }
  updateToAdmin(username:string){
    return this.http.patch(environment.apiUrl+"/userCredential/role/setAdmin/"+username,{})
  }

   updateStatusBan(userId:any){
    return this.http.patch(environment.apiUrl+"/userCredential/update/status/ban/"+userId,{})
  }

   updateStatusUnBan(userId:any){
    return this.http.patch(environment.apiUrl+"/userCredential/update/status/unban/"+userId,{})
  }

   deleteUserById(userId:any){
    return this.http.delete(environment.apiUrl+"/userCredential/delete/"+userId)
  }

  getByUserId(userId:any){
    return this.http.get(environment.apiUrl+"/userCredential/get/"+userId)
  }

  updateEditProfile(user:any){
    return this.http.patch(environment.apiUrl+"/userCredential/update",user)
  }

  updatePassword(userWithPassword:any){
    return this.http.patch(environment.apiUrl+"/userCredential/update/password",userWithPassword)
  }


}
