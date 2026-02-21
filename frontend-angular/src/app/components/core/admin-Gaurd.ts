import { CanActivateFn, Router } from "@angular/router";
import { inject } from "@angular/core";
import { AuthServiceService } from "../../services/auth-service.service";

export const adminguard:CanActivateFn=(route,state)=>{
  const router=inject(Router)
  const authService=inject(AuthServiceService)
   
  if(authService.isAdmin){
    return true
  }
  else{
    router.navigateByUrl('/')
    return false;
  }

}