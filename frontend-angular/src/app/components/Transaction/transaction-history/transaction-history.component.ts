import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { CommonModule } from '@angular/common';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { TransactionService } from '../../../services/transaction/transaction.service';
import { MatInputModule } from '@angular/material/input';
import { AccountService } from '../../../services/account.service';
import { map } from 'rxjs';
import { MatDateRangeInput, MatDateRangePicker, MatDatepicker, MatDatepickerToggle, MatEndDate, MatStartDate } from '@angular/material/datepicker';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { provideNativeDateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-transaction-history',
  standalone: true,
  providers: [provideNativeDateAdapter()],
  imports: [CommonModule,
            MatFormField,
            MatLabel,
            MatCardModule,
            MatPaginator,
            MatTableModule,
            MatInputModule,
            MatDateRangeInput,
            MatDatepicker,
            MatDateRangePicker,
            MatFormField,
            MatDatepickerToggle,
            ReactiveFormsModule,
            MatStartDate,
            MatEndDate

          ],
  templateUrl: './transaction-history.component.html',
  styleUrl: './transaction-history.component.css'
})
export class TransactionHistoryComponent {
   range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });
  displayedColumns: string[] = [
    'TransactionId',
    'fromAccFullName',
    'fromoAccId',
    'amount',
    'txnType',
    'status',
    'toAccountId',
    'deviceId',
    'DateAndTime',
    'toAccFullName'
  ];

  dataSource = new MatTableDataSource<any>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  transactions: any[] = []
  accountIds: any[] = []
  user: any

  constructor(private transactionService: TransactionService, private accountService: AccountService) { }



  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('user') as any)
    this.accountService.getAllAccounts(this.user.id).subscribe((res: any) => {
      this.accountIds = res.map((item: any) => {
        return item.accountId
      })
      console.log(this.accountIds)

      console.log(this.accountIds)
      this.transactionService.getTransactions(this.accountIds, this.accountIds).subscribe((res: any) => {
        console.log(res)
        this.dataSource.data= res.map((item: any) => ({
          ...item,                            // keep all other properties
          account: JSON.parse(item.account),  // parse account string into object
          device: JSON.parse(item.device),     // optional: parse device if needed
          timeStamp: new Date(item.timeStamp) // converts string to Date
        }));


      })

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

  getStatusClass(status: string) {
    switch (status) {
      case 'SUCCESS':
        return 'bg-green-100 text-green-700';
      case 'FAILED':
        return 'bg-red-100 text-red-700';
      default:
        return '';
    }

  }

   filterByDate() {
    this.dataSource.filterPredicate = (data: any, filter: string) => {
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

   }




}
