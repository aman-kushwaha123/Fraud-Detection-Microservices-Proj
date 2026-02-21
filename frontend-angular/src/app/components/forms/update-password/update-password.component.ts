import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule,Location } from '@angular/common';
import { Router } from '@angular/router';
import { ToastrService } from "ngx-toastr";
import { UserService } from '../../../services/user/user.service';
@Component({
  selector: 'app-update-password',
  standalone: true,
  imports: [
     CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './update-password.component.html',
  styleUrl: './update-password.component.css'
})
export class UpdatePasswordComponent {
  hidePassword = true;
  user=JSON.parse(localStorage.getItem('user') as any)

  formbuilder = inject(FormBuilder)
  updatePasswordForm = this.formbuilder.group({
    id:[this.user.id],
    password: ['', [Validators.required, Validators.minLength(5)]],
  })

   constructor(private router:Router,private toastrService:ToastrService,private userService:UserService,private location:Location){}

  submit() {
     let values=this.updatePasswordForm.value
     this.userService.updatePassword(values).subscribe({
      next:(res:any)=>{
        console.log(res.user)
        this.router.navigateByUrl('/profile')
        this.toastrService.success("Your Password Successfully Updated",'success')
      },
      error:(err)=>{
        this.toastrService.error("Something went wrong",'error');
        this.router.navigateByUrl('/profile')
      }

     })
    }

    goback(){
      this.location.back()
    }
}
