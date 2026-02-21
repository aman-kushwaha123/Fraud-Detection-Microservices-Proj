import { Component,inject } from '@angular/core';
import { CommonModule,Location } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { FormBuilder, FormsModule, NgModel, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { ToastrService } from "ngx-toastr";
import { UserService } from '../../../services/user/user.service';

@Component({
  selector: 'app-edit-profile',
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
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.css'
})
export class EditProfileComponent {
  user=JSON.parse(localStorage.getItem('user') as any)
  formbuilder=inject(FormBuilder)
    editProfileForm=this.formbuilder.group(
      {
        id:[this.user.id],
        username:[this.user.username,[Validators.required,Validators.minLength(5),Validators.pattern('^[a-zA-Z ]+$')]],
        email:[this.user.email,[Validators.required,Validators.email]],
        mobileNo:[this.user.mobileNo,[Validators.required,Validators.minLength(10),Validators.pattern('^[0-9]*$')]]
      }
    )

     constructor(private router:Router,private toastrService:ToastrService,private userService:UserService,private location:Location){}

    onSubmit() {
     let values=this.editProfileForm.value
     this.userService.updateEditProfile(values).subscribe({
      next:(res:any)=>{
        console.log(res.user)
        localStorage.setItem("user",JSON.stringify(res.user))
        this.router.navigateByUrl('/profile')
        this.toastrService.success("You Successfully Updated Your Profile",'success')
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


