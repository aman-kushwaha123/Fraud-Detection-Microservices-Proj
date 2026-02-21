import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { AuthServiceService } from '../../services/auth-service.service';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MatCardModule,MatButtonModule,RouterLink],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user:any

  constructor(private authService:AuthServiceService){}

  ngOnInit(){
    this.user=JSON.parse(localStorage.getItem('user') as any)
  }

  logout(){
     this.authService.logout()
  }



}
