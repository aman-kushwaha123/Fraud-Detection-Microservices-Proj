import { CommonModule ,Location} from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
import { DeviceHistoryService } from '../../../services/device-history.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-device-history',
  standalone: true,
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
  templateUrl: './device-history.component.html',
  styleUrl: './device-history.component.css'
})
export class DeviceHistoryComponent {
  displayedColumns: string[] = [
    'deviceId',
    'userId',
    'lastUsed',
    'visitorId',
    'riskLevel',
    'deletedeviceHistory'
  ];

  dataSource = new MatTableDataSource<any>([]);
  riskLevelFilter = '';
  dateFilter = null

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private deviceHistoryService:DeviceHistoryService
             ,private location:Location
             ,private router:Router
             ,private userService:UserService
             ,private toastrService:ToastrService){}


  ngOnInit() {
    this.deviceHistoryService.getAllDevices().subscribe((res: any) => {
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

   filterByRiskLevel() {
    this.dataSource.filterPredicate = (data, filter) =>                 //runs for each row This is a function that runs for each row. data =row object
      !filter || data.riskLevel === filter;
    this.dataSource.filter = this.riskLevelFilter;
  }

  goBack(){
   this.location.back()
  }

  deleteDeviceHistory(deviceId:any){
    this.deviceHistoryService.deleteDeviceById(deviceId).subscribe((res)=>{
      console.log(res)
      this.router.navigateByUrl('/adminDashboard')
      this.toastrService.success("Successfully Device Deleted","success")
    })

  }








}
