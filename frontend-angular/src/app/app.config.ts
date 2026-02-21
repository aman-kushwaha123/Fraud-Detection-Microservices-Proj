import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { tokenHttpInterceptor } from './components/core/token-http-interceptor';
import { provideAnimations } from '@angular/platform-browser/animations';
import {provideToastr} from 'ngx-toastr';
export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes)
    , provideAnimationsAsync()
    ,provideAnimations()
    ,provideHttpClient()
    ,provideHttpClient(withInterceptors([tokenHttpInterceptor]))
    ,provideToastr({
      timeOut:5000,
      positionClass:'toast-top-right',
      preventDuplicates:true
    })
  ]
};
