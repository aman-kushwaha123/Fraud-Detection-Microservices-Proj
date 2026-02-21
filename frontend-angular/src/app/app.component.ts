import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSidenavModule, MatDrawer } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { AuthServiceService } from './services/auth-service.service';
import { FooterComponent } from './components/footer/footer.component';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,MatSlideToggleModule
    , MatSidenavModule
    , MatToolbarModule
    , MatIconModule
    , MatButtonModule
    , MatListModule
    , RouterLink
    , CommonModule
    , FooterComponent


  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend-angular';
  user:any

  constructor(private authService:AuthServiceService,private router:Router){
  }

  ngOnInit(){
    this.router.events
    .pipe(filter(event => event instanceof NavigationEnd))
    .subscribe(() => {
      this.user = JSON.parse(localStorage.getItem('user') as any);
    });
  }

  logout(){
    this.authService.logout()
  }

  isLoggedIn(){
    return this.authService.isLoggedIn
  }

  isAdmin(){
    return this.authService.isAdmin
  }



}
