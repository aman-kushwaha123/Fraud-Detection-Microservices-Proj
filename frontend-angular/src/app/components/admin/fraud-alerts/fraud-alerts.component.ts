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
import { AlertServiceService } from '../../../services/alert/alert-service.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { provideNativeDateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-fraud-alerts',
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
  templateUrl: './fraud-alerts.component.html',
  styleUrl: './fraud-alerts.component.css'
})
export class FraudAlertsComponent {
  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });
     displayedColumns: string[] = [
    'alertId',
    'txnId',
    'userId',
    'alertMessage',
    'timeStamp',
    'status',
    'deleteAlert',
  ];

  dataSource = new MatTableDataSource<any>([]);
  statusFilter = '';
  dateFilter = ''

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private alertService:AlertServiceService,private location:Location,
              private router:Router,
              private toastrService:ToastrService
  ){}


  ngOnInit() {
    this.alertService.getAllAlerts().subscribe((res: any) => {
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

    console.log( this.dataSource.data[0].timeStamp);

  }

  goBack(){
   this.location.back()
  }

  deleteAlert(alertId:any){
    this.alertService.deletAlertById(alertId).subscribe((res:any)=>{
      console.log(res)
      this.router.navigateByUrl('/adminDashboard')
      this.toastrService.success("Successfully Alert Deleted","success")
    })
  }

}
