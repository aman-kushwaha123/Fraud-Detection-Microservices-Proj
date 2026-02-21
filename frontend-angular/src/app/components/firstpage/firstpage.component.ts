import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';

@Component({
  selector: 'app-firstpage',
  standalone: true,
  imports: [MatButtonModule,MatCardModule],
  templateUrl: './firstpage.component.html',
  styleUrl: './firstpage.component.css'
})

export class FirstpageComponent {

  constructor(private router:Router){

  }
  goToLogin(){
    this.router.navigateByUrl('/login');
  }
  goToRegister(){
    this.router.navigateByUrl('/register');
  }

}
