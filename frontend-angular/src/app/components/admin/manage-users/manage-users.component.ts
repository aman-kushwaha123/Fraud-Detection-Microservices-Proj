import { CommonModule,Location } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { AccountService } from '../../../services/account.service';
import { UserService } from '../../../services/user/user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { provideNativeDateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-manage-users',
  standalone: true,
  providers: [provideNativeDateAdapter()],
  imports: [
     CommonModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatDatepickerModule,
    ReactiveFormsModule
  ],
  templateUrl: './manage-users.component.html',
  styleUrl: './manage-users.component.css'
})
export class ManageUsersComponent {
   range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });
  displayedColumns: string[] = [
    'id',
    'username',
    'email',
    'mobileNo',
    'roles',
    'status',
    'createdAt',
    'updatetoAdmin',
    'updateUserStatus',
     'deleteUser'
  ];

  dataSource = new MatTableDataSource<any>([]);
  roleFilter = '';

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private userService:UserService,private location:Location,private router:Router,private toastrService:ToastrService){}


  ngOnInit() {
    this.userService.getAllUsers().subscribe((res: any) =>{
      this.dataSource.data = res
    })
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
  goBack(){
   this.location.back()
  }

  updatetoAdmin(){
    let user=JSON.parse(localStorage.getItem('user')as any)
    this.userService.updateToAdmin(user.username).subscribe((res)=>{
      console.log(res)
       this.router.navigateByUrl('/adminDashboard')
    })
  }

  filterByRole(){
    this.range.reset({
      start: null,
      end: null
    });
   this.dataSource.filterPredicate = (data: any, filter: string) => {
  const search = filter.trim().toLowerCase();

  return data.roles?.some(
    (r: any) => r.roleName?.toLowerCase() === search
  ) ?? false;
};
   this.dataSource.filter = this.roleFilter; // <- this actually filters the table
  }




  updateUserStatusBan(userId:any){
    this.userService.updateStatusBan(userId).subscribe((res:any)=>{
      this.router.navigateByUrl('/adminDashboard')
      this.toastrService.success("Successfully Banned","success")
    })
  }

  updateUserStatusUnBan(userId:any){
    this.userService.updateStatusUnBan(userId).subscribe((res:any)=>{
      this.router.navigateByUrl('/adminDashboard')
        this.toastrService.success("Successfully UnBanned","success")
    })
  }




  deleteUser(userId:any){
    this.userService.deleteUserById(userId).subscribe((res:any)=>{
      this.router.navigateByUrl('/adminDashboard')
       this.toastrService.success("Successfully User Deleted","success")
    })

  }

   filterByDate() {
    this.dataSource.filterPredicate = (data: any, filter: string) => {
      if (this.roleFilter != '') {
        this.roleFilter = ''
      }
      const search = JSON.parse(filter);

      const dataDate = new Date(data.createdAt);
      dataDate.setHours(0, 0, 0, 0); // remove time

      if (search.start) {
        const startDate = new Date(search.start);
        startDate.setHours(0, 0, 0, 0);

        if (dataDate < startDate) return false;   //If true → the row is before the start date, so it should not appear → return false.
      }

      if (search.end) {
        const endDate = new Date(search.end);
        endDate.setHours(0, 0, 0, 0);

        if (dataDate > endDate) return false;
      }

      return true;
    };


    this.dataSource.filter = JSON.stringify({
      start: this.range.get('start')?.value || null,
      end: this.range.get('start')?.value || null
    });

    console.log(typeof this.dataSource.data[0].timeStamp);

  }


}
