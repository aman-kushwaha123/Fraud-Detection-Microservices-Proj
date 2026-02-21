import { HttpClient } from '@angular/common/http';
import { Injectable,inject } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class PredictService {


  constructor(private router:Router,private http:HttpClient) { }

  getPrediction(){
    return this.http.get(environment.apiUrl+"/Fraud/predict")
  }

}
