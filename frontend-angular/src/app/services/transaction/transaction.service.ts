import { HttpClient } from '@angular/common/http';
import { Injectable,inject } from '@angular/core';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  http=inject(HttpClient)

  constructor() { }

  addTransaction(values:any){
    return this.http.post(environment.apiUrl+"/transaction/add",values)
  }

   getTransactions( fromAccountIds:any,toAccountIds?:any){
    console.log(fromAccountIds,toAccountIds)
    return this.http.get(environment.apiUrl+"/transaction/all/transactions/"+fromAccountIds+'/'+toAccountIds)
  }
  getTransactionsByFrom( fromAccountIds:any){
    console.log(fromAccountIds)
    return this.http.get(environment.apiUrl+"/transaction/all/transactions/"+fromAccountIds)
  }

  getAllTransactions(){
    return this.http.get(environment.apiUrl+"/transaction/all/transactions")
  }

  deleteTxnById(txnId:any){
    return this.http.delete(environment.apiUrl+"/transaction/delete/"+txnId)

  }
}
