import { HttpClient } from '@angular/common/http';
import { Injectable,inject} from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  http=inject(HttpClient)
  router=inject(Router)


  constructor() { }
  register(values:object){
        return this.http.post(environment.apiUrl+"/userCredential/register",values)
  }

  login(values:object){
    return this.http.post(environment.apiUrl+"/userCredential/login",values)
  }

   istokenExpired(token:any){
    return this.http.get(environment.apiUrl+'/userCredential/isTokenexpired/'+token)
  }


  get isLoggedIn(){
    const token=localStorage.getItem('token');
    if (!token){
      return false;
    }
      return true;

  }

  logout(){
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    localStorage.removeItem('device')
    this.router.navigateByUrl('/')

  }

  get isAdmin(){
    const user=localStorage.getItem('user') as any
      if(user!==null){
        if(JSON.parse(user).roles[0].roleName==="ADMIN"){
             return true;
        }else{
          return false;
        }
      }
      return false;


  }


  get isBanned(){
    const user=localStorage.getItem('user') as any
      if(user!==null){
        if(JSON.parse(user).status==="BANNED"){
             return true;
        }else{
          return false;
        }
      }
      return false;

  }


}
