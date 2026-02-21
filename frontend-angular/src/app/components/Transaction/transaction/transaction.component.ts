import { Component, inject } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { AccountService } from '../../../services/account.service';
import { filter, map, Observable, startWith } from 'rxjs';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { User } from '../../../types/user';
import { CurrentLocation } from '../../../types/CurrentLocation';
import { LocationService } from '../../../services/location/location.service';
import { TransactionService } from '../../../services/transaction/transaction.service';
import { Account } from '../../../types/Account';
import { AsyncPipe } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../../dialog/dialog.component';
import { WebsocketService } from '../../../services/websocket/websocket.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { PredictService } from '../../../services/predict/predict.service';
import { DeviceHistoryService } from '../../../services/device-history.service';
import { ToastrService } from 'ngx-toastr';



@Component({
  selector: 'app-transaction',
  standalone: true,
  imports: [
    MatCardModule,
    MatSelectModule,
    ReactiveFormsModule,
    AsyncPipe,
    MatAutocompleteModule,
    MatIconModule,
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css'
})
export class TransactionComponent {
  accountService = inject(AccountService)
  formbuilder = inject(FormBuilder)
  locationService = inject(LocationService)
  transactionService = inject(TransactionService);
  predictService = inject(PredictService)
  deviceHistoryService=inject(DeviceHistoryService)
  router = inject(Router)

  websocketService = inject(WebsocketService)
  alerts: any[] = []

  userAccounts?: Account[] = []
  transactionType?: any[] = ["UPI", "CARD", "ATM"]


  user?: any
  device?: any
  WalletBalance = 0
  selectedAccount: any
  selectedRecipientAcc: any

  transactionForm = this.formbuilder.group({
    account: new FormControl<Account | null>(null, { validators: [Validators.required] }),
    amount: [0, [Validators.required, Validators.min(1), Validators.pattern('^[0-9]*$')]],
    txnType: ['', [Validators.required]],
    toAccount: new FormControl<Account | string | null>(null, { validators: [Validators.required] }),
    location: new FormControl<CurrentLocation | string | null>(null, { validators: [Validators.required] }),
  })

  //set value into auto complete

  constructor(private dialog: MatDialog, private location: Location, private toastrService: ToastrService) {

  }

  allaccounts: Account[] = []
  filteredOptions!: Observable<Account[]>;

  ngOnInit() {
    if (!this.websocketService.isConnected()) {
      console.log("processing connection..")
      this.websocketService.connect(msg => {
        if (msg) {
          this.alerts.push(msg);
           console.log(msg)
           this.openDialog()
      }
      console.log("no mesg")
    })
  }

    this.user = JSON.parse(localStorage.getItem("user") as any)
    console.log(this.user)
    this.device = JSON.parse(localStorage.getItem("device") as any)

    this.accountService.getAllAccounts(this.user.id).subscribe((res: any) => {
      this.userAccounts = res
    })
    this.accountService.allAccounts().subscribe((res: any) => {
      this.allaccounts = res
    })
    //whenever the value of the form control changes,we filter the user list
    this.filteredOptions = this.transactionForm.get('toAccount')!.valueChanges.pipe(
      startWith(''),
      map(value => {
        const search = typeof value === 'string' ? value : value?.username;

        //if search text exists,filter users; else return full list
        return search ? this._filter(search) : this.allaccounts
      })
    )


  }

  setCurrentLocation(): void {
    this.locationService.getCurrentLocation().subscribe({
      next: (location: CurrentLocation) => {
        //set the object directly into autocomplete control
        this.transactionForm.controls.location.setValue(location);
      },
      error: err => {
        console.log('Location error', err);
      }
    })
  }

  displayLocation(value: CurrentLocation | string | null): string {
    if (!value) return '';
    if (typeof value === 'string') return value;
    if ('label' in value) return value.label;
    return '';
  }


  private _filter(name: string): Account[] {
    //Convert to lowercase for case-insensitive match
    const filterValue = name.toLowerCase();
    console.log(filterValue)
    console.log(this.allaccounts)
    return this.allaccounts.filter(account => account.fullName?.toLowerCase().includes(filterValue));
  }

  //This tells Angular Material what to show in the input afte selection
  displayFn(account: Account): string {
    return account ? account.username : '';
  }



  goBack() {
    this.websocketService.disconnect();
    this.location.back();

  }



  submit() {
    let values = this.transactionForm.value
    let is_Fraud: Boolean = false
    let toAccount=typeof values.toAccount !== 'string' ? values.toAccount : null
    let transaction = {
      account:JSON.stringify(values.account),
      fromAccountId:values.account?.accountId,
      amount: values.amount,
      txnType: values.txnType,
      toAccountId: typeof values.toAccount !== 'string' ? values.toAccount?.accountId : 0,
      toAccFullname : typeof values.toAccount !== 'string' ? values.toAccount?.fullName : '',
      location: JSON.stringify(values.location),
      amountType:values.account?.accountId===toAccount?.accountId? 'CREDITED':'DEBITED',
      device: JSON.stringify(this.device),
      userId: this.user.id
    }
    console.log(transaction)
    this.deviceHistoryService.deviceCount(transaction.userId).subscribe((res)=>{
      console.log(res)
    })
     this.transactionService.addTransaction(transaction).subscribe((res: any) => {
      console.log(res)
      this.predictService.getPrediction().subscribe({
        next: (res:any) => {
          console.log("prediction")
          console.log(res)
          is_Fraud = res.is_Fraud;
          if (is_Fraud != true) {
            this.router.navigateByUrl('/transaction/history')
            this.toastrService.success("Successfully Trasferred Money","Success")
          }
        },
        error: (err) => {
          console.log(err)
        }
      }
    )

    }
  )

  }

  openDialog() {
    if (this.dialog.openDialogs.length > 0) {
      console.log("multiple dialog opened")
    return; // Prevent multiple dialogs
  }
    const dialogRef =
      this.dialog.open(DialogComponent, {
        width: '400px',
        data: {
          messages: this.alerts[this.alerts.length - 1],
        }

      });
  }





}
