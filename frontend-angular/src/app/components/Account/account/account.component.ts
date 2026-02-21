import { Component,inject} from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { AccountService } from '../../../services/account.service';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [MatCardModule,MatButtonModule,MatListModule,RouterLink,CommonModule],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent {
  accountService=inject(AccountService)
  accounts?:any[]=[]
  selectedAccount?: any;

  constructor(){
    this.allAccounts()
    
  }


    allAccounts(){
      let user=JSON.parse(localStorage.getItem('user') as any)
      this.accountService.getAllAccounts(user.id).subscribe((res:any)=>{
            console.log(res)
            this.accounts=res
            console.log(this.accounts)
      })


    }


  selectAccount(account: any) {
    this.selectedAccount = account;
  }
}
