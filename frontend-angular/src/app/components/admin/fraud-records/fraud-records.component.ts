import { CommonModule ,Location, NgFor} from '@angular/common';
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
import { AlertServiceService } from '../../../services/alert/alert-service.service';
import { FraudRecordsService } from '../../../services/fraud-rec/fraud-records.service';
import { Router } from '@angular/router';
import { UserService } from '../../../services/user/user.service';
import { ToastrService } from 'ngx-toastr';
import { provideNativeDateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-fraud-records',
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
    ReactiveFormsModule,
  ],
  templateUrl: './fraud-records.component.html',
  styleUrl: './fraud-records.component.css'
})
export class FraudRecordsComponent {
   range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });
  displayedColumns: string[] = [
    'caseId',
    'txnId',
    'userId',
    'timeStamp',
    'reviewer',
    'remark',
    'status',
   'fraud_probability',
   'updateReviewer',
   'deleteFraudRec'
  ];

  dataSource = new MatTableDataSource<any>([]);
  statusFilter = '';
  dateFilter = null

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private fraudRecService:FraudRecordsService
             ,private location:Location
             ,private router:Router
             ,private userService:UserService
             ,private toastrService:ToastrService){}


  ngOnInit() {
    this.fraudRecService.getAllFraudRecords().subscribe((res: any) => {
      this.dataSource.data = res;
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

   filterByStatus() {
     this.range.reset({
      start: null,
      end: null
    });
    this.dataSource.filterPredicate = (data, filter) =>                 //runs for each row This is a function that runs for each row. data =row object
      !filter || data.status === filter;
    this.dataSource.filter = this.statusFilter;
  }

  filterByDate() {
    this.dataSource.filterPredicate = (data: any, filter: string) => {
      if (this.statusFilter != '') {
        this.statusFilter = ''
      }
      const search = JSON.parse(filter);

      const dataDate = new Date(data.timeStamp);
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

  goBack(){
   this.location.back()
  }

  getType(value: any): string {
  return typeof value;
}

  updateStatus(caseId:any){
    let user=JSON.parse(localStorage.getItem('user') as any)
    this.fraudRecService.updateStatus(caseId,user.username).subscribe((res:any)=>{
      this.router.navigateByUrl('/adminDashboard')
      this.toastrService.success("Successfully updated Reviewer","success")

    })
  }

   deleteAlert(caseId:any){
    this.fraudRecService.deleteFraudRecById(caseId).subscribe((res:any)=>{
      console.log(res)
      this.router.navigateByUrl('/adminDashboard')
      this.toastrService.success("Successfully FraudRecord Deleted","success")
    })
  }

}



