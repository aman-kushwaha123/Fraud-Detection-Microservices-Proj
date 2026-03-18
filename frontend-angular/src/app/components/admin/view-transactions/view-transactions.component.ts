import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { TransactionService } from '../../../services/transaction/transaction.service';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { formatDate } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-view-transactions',
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
  templateUrl: './view-transactions.component.html',
  styleUrl: './view-transactions.component.css'
})
export class ViewTransactionsComponent {
  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  displayedColumns: string[] = [
    'txnId',
    'fromAccountId',
    'fromFullname',
    'amount',
    'txnType',
    'status',
    'toAccFullname',
    'toAccountId',
    'location',
    'deviceId',
    'timeStamp',
    'userId',
    'isFraud',
    'deleteTransaction'
  ];
  transactions: any[] = []

  dataSource = new MatTableDataSource<any>([]);
  statusFilter = '';

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private transactionService: TransactionService
    , private location: Location,private router:Router,private toastrService:ToastrService) {

  }

  ngOnInit() {
    this.transactionService.getAllTransactions().subscribe((res: any) => {
      this.transactions = res.map((item: any) => ({
        ...item,                            // keep all other properties
        account: JSON.parse(item.account),  // parse account string into object
        device: JSON.parse(item.device)     // optional: parse device if needed
      }));
      this.dataSource.data = this.transactions;
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

  goBack() {
    this.location.back()
  }

  deleteTransaction(txnId:any){
    this.transactionService.deleteTxnById(txnId).subscribe((res:any)=>{
      console.log(res)
      this.router.navigateByUrl('/adminDashboard')
       this.toastrService.success("Successfully Transaction Deleted","success")
    })
  }


}


