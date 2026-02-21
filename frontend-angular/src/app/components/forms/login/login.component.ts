import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { AuthServiceService } from '../../../services/auth-service.service';
import { DeviceHistoryService } from '../../../services/device-history.service';
import FingerprintJS from '@fingerprintjs/fingerprintjs';
import { Router } from '@angular/router';
import { ToastrService } from "ngx-toastr";
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,

  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  hidePassword = true;

  deviceHistoryService = inject(DeviceHistoryService)
  authService = inject(AuthServiceService)
  formbuilder = inject(FormBuilder)
  loginform = this.formbuilder.group({
    username: ['', [Validators.required, Validators.minLength(5), Validators.pattern('^[a-zA-Z ]+$')]],
    password: ['', [Validators.required, Validators.minLength(5)]],
  })


  constructor(private router: Router, private toastrService: ToastrService) {

  }

  async getFingerprint(): Promise<string> {
    const fp = await FingerprintJS.load();
    const result = await fp.get();
    return result.visitorId;
  }

  async submit() {
    let values = this.loginform.value
    let userId:any
    let user:any
    let vistrId = await this.getFingerprint();
    this.authService.login(values).subscribe({

    next:(res:any)=>{
      localStorage.setItem("token", res.token)
      user=res.user
      localStorage.setItem("user", JSON.stringify(res.user))
      userId=res.user.id
      console.log("successfully save token", res.token, "user", res.user)

      let device = {
        userId: res.user.id,
        visitorId: vistrId
      }

      console.log(device)
      this.deviceHistoryService.devicelogin(device).subscribe({
        next: (res: any) => {
          console.log(res.device)
          localStorage.setItem("device", JSON.stringify(res.device))
          this.deviceHistoryService.deviceCount(userId).subscribe((res) => {
            console.log(res)
          })
          this.router.navigateByUrl('/home')
          console.log(user.status)
          if(user.status!='BANNED'){
          this.toastrService.success('Successfully logged In', 'Success')
          }
        },
        error: (err) => {
          this.router.navigateByUrl('/')
          this.toastrService.error('device not Found')
        }
      })
    },
    error:(err)=>{
      this.router.navigateByUrl('/')
      this.toastrService.error('User not Found')
    }
  })

}

}
