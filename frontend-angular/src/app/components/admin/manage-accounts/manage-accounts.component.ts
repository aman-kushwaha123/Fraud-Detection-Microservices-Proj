import { CommonModule,Location} from '@angular/common';
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
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { provideNativeDateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-manage-accounts',
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
  templateUrl: './manage-accounts.component.html',
  styleUrl: './manage-accounts.component.css'
})
export class ManageAccountsComponent {
    range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });
   displayedColumns: string[] = [
    'accountId',
    'userId',
    'username',
    'createdAt',
    'mobileNo',
    'accountNumber',
    'accountType',
    'accountBalance',
    'fullName',
    'deleteAccount'
  ];
  accounts: any[] = []

  dataSource = new MatTableDataSource<any>([]);
  statusFilter = '';
  dateFilter = null

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private accountService:AccountService,private location:Location,private router:Router,private toastrService:ToastrService){}

   ngOnInit() {
    this.accountService.allAccounts().subscribe((res: any) => {
      this.accounts = res
      this.dataSource.data = this.accounts;
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

  filterByType() {
    this.range.reset({
      start: null,
      end: null
    });
    this.dataSource.filterPredicate = (data, filter) =>                 //runs for each row This is a function that runs for each row. data =row object
      !filter || data.accountType === filter;
    this.dataSource.filter = this.statusFilter;
  }
  goBack(){
   this.location.back()
  }

  deleteAccount(accountId:any){
    this.accountService.deleteAccountById(accountId).subscribe((res:any)=>{
      console.log(res)
       this.router.navigateByUrl('/adminDashboard')
       this.toastrService.success("Successfully Account Deleted","success")
    })
  }

   filterByDate() {
    this.dataSource.filterPredicate = (data: any, filter: string) => {
      if (this.statusFilter != '') {
        this.statusFilter = ''
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
