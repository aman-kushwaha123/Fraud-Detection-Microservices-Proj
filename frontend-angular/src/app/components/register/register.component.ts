import { Component,inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthServiceService } from '../../services/auth-service.service';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { ToastrService } from "ngx-toastr";
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    ReactiveFormsModule,
    MatCardModule,
    FormsModule,

  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
    hidePassword = true;
    authService=inject(AuthServiceService)
    formbuilder=inject(FormBuilder)
    registerForm=this.formbuilder.group(
      {
        username:['',[Validators.required,Validators.minLength(5),Validators.pattern('^[a-zA-Z ]+$')]],
        email:['',[Validators.required,Validators.email]],
        password:['',[Validators.required,Validators.minLength(5)]],
        mobileNo:['',[Validators.required,Validators.minLength(10),Validators.pattern('^[0-9]*$')]]
      }
    )
    constructor(private router:Router,private toastrService:ToastrService){

    }
  onSubmit() {
     let values=this.registerForm.value
     this.authService.register(values).subscribe({
      next:(res)=>{
        console.log(res)
        console.log("Sucessfully registered",values)
        this.router.navigateByUrl('login')
        this.toastrService.success("You Successfully Regitered Please login",'success')
      },
      error:(err)=>{
        this.toastrService.error("Something went wrong",'error');
        this.router.navigateByUrl('/home')
      }

     })
  }

}
