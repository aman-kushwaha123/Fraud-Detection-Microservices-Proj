import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { DeviceHistoryService } from '../../services/device-history.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatCardModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private devicehistoryService:DeviceHistoryService){}

  ngOnInit(){
    let user=JSON.parse(localStorage.getItem('user') as any);
   
  }


}
