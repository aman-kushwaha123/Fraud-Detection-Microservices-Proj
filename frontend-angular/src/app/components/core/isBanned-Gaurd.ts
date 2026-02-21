import { CanActivateFn, Router } from "@angular/router";
import { inject } from "@angular/core";
import { AuthServiceService } from "../../services/auth-service.service";
import { ToastrService } from 'ngx-toastr';

export const isBannedgaurd:CanActivateFn=(route,state)=>{
  const router=inject(Router)         //route is current url and state is trying to access url
  const authService=inject(AuthServiceService)
  const toastService=inject(ToastrService)

  if(!authService.isBanned){
    return true
  }
  else{
    authService.logout()
    toastService.warning("Malicious Activity Seems","Banned")
    return false;
  }

}
