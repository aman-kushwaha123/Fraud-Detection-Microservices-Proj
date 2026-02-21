import { Component,Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import {
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogTitle,
  MAT_DIALOG_DATA,
  MatDialogRef,
} from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dialog',
  standalone: true,
  imports: [MatDialogContent,MatDialogActions,MatDialogClose,MatDialogTitle,MatButtonModule,CommonModule],
  templateUrl: './dialog.component.html',
  styleUrl: './dialog.component.css'
})
export class DialogComponent {


   constructor(
    public dialogRef:MatDialogRef<DialogComponent>,@Inject(MAT_DIALOG_DATA) public data:any,
    private router:Router
   ){};

   ngOnInit(){

   }

   close(){
     this.dialogRef.close(true)
     this.dialogRef.afterClosed().subscribe(result => {
     if (result) {
      this.router.navigateByUrl('/home');
     }
    });
  }







}
