import { inject } from "@angular/core";
import { CanActivateFn } from "@angular/router";
import { Router } from "@angular/router";
import { AuthServiceService } from "../../services/auth-service.service";


export const isLoggedguard:CanActivateFn=(route,state)=>{
   const router=inject(Router);
   
   const authService=inject(AuthServiceService)
   if(!authService.isLoggedIn){
    router.navigateByUrl('/')
     return false;
     
   }
   
     return true;
   

}