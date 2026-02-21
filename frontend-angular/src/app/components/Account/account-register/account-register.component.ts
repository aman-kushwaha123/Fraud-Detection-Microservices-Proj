import { Component,inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { AccountService } from '../../../services/account.service';
import { MatSelectionList } from '@angular/material/list';
import {MatSelectModule} from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { CommonModule,Location } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import {MatCheckboxModule} from '@angular/material/checkbox';
@Component({
  selector: 'app-account-register',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    MatCardModule,
    RouterLink,
    CommonModule,
    MatCheckboxModule
  ],
  templateUrl: './account-register.component.html',
  styleUrl: './account-register.component.css'
})
export class AccountRegisterComponent {
  formbuilder=inject(FormBuilder)
  accountService=inject(AccountService)
  accountTypeList:any[]=["SAVING","CURRENT"]
  accountRegister=this.formbuilder.group({
    fullname:['',[Validators.required,Validators.minLength(5),Validators.pattern('^[a-zA-Z ]+$')]],
    mobileNo:['',[Validators.required,Validators.minLength(10),Validators.pattern('^[0-9]*$')]],
    accountType:[null,[Validators.required]],
    accountNumber:['',[Validators.required,Validators.minLength(15),Validators.pattern('^[0-9]*$')]],
    accountBalance:[0,[Validators.required,Validators.minLength(1),Validators.pattern('^[0-9]*$')]]
  });

  constructor(private router:Router,private location:Location){}

  goBack(){
   this.location.back()
  }

  submit(){
    let values=this.accountRegister.value
    let user=JSON.parse(localStorage.getItem('user') as any);
    let account={
      userId:user.id,
      username:user.username,
      mobileNo:values.mobileNo,
      accountType:values.accountType,
      accountNumber:values.accountNumber,
      accountBalance:values.accountBalance,
      fullName:values.fullname
    }
    this.accountService.addAccount(account).subscribe((res)=>{
         console.log(res)
         this.router.navigateByUrl('/accounts')


    })



  }


}
