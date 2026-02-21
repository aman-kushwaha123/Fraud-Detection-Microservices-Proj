import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { AuthServiceService } from "../../services/auth-service.service";
import { map, catchError } from 'rxjs/operators';
import {finalize, Observable, throwError,of } from "rxjs";
export const tokenExpguard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthServiceService)
  const toastrService=inject(ToastrService)
  if (!authService.isLoggedIn) {
    return router.parseUrl('/');
  }
  const token =localStorage.getItem('token')
    if(!token){
      toastrService.show("Please re-login","Re-Login")
      authService.logout()
       return router.parseUrl('/');
    }

  return authService.istokenExpired(token).pipe(
    map((res: any) => {
      if (res.isTokenExpired) {
        toastrService.show('Please re-login', 'Re-Login');
        authService.logout();
        return router.parseUrl('/');
      }
      return true;
    }),
    catchError(() => {
      toastrService.show('Please re-login', 'Re-Login');
      authService.logout();
      return of(router.parseUrl('/'));
    }))



}
