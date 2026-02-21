import { HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { Router } from "@angular/router";
import { map} from 'rxjs/operators';
import { catchError, finalize, Observable, throwError } from "rxjs";
import { AuthServiceService } from "../../services/auth-service.service";
export const tokenHttpInterceptor: HttpInterceptorFn = (req, next)=>{
  const authService=inject(AuthServiceService)
  const token = localStorage.getItem('token')

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    })
  }         //to intercept in server req
   
  return next(req).pipe(
    catchError((error) => {
      let result ;
      authService.istokenExpired(token).subscribe((res:any)=>{
      result=res.isTokenExpired;
    })
      if (
        error.status === 401 &&
        token &&
        result) {
        const router = inject(Router)
        localStorage.removeItem('token')
        localStorage.removeItem('user')
        localStorage.removeItem('device')
        router.navigateByUrl('/')

      }
    // Re-throw the original error
    return throwError(() => error)
    })
    )

}