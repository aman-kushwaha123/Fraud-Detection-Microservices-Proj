import { Routes } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/forms/login/login.component';
import { AccountComponent } from './components/Account/account/account.component';
import { TransactionComponent } from './components/Transaction/transaction/transaction.component';
import { AccountRegisterComponent } from './components/Account/account-register/account-register.component';
import { isLoggedguard } from './components/core/isLogged-Gaurd';
import { tokenExpguard } from './components/core/tokenExpired-Gaurd';
import { FirstpageComponent } from './components/firstpage/firstpage.component';
import { TransactionHistoryComponent } from './components/Transaction/transaction-history/transaction-history.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { ViewTransactionsComponent } from './components/admin/view-transactions/view-transactions.component';
import { ManageAccountsComponent } from './components/admin/manage-accounts/manage-accounts.component';
import { ManageUsersComponent } from './components/admin/manage-users/manage-users.component';
import { FraudRecordsComponent } from './components/admin/fraud-records/fraud-records.component';
import { FraudAlertsComponent } from './components/admin/fraud-alerts/fraud-alerts.component';
import { adminguard } from './components/core/admin-Gaurd';
import { DeviceHistoryComponent } from './components/admin/device-history/device-history.component';
import { isBannedgaurd } from './components/core/isBanned-Gaurd';
import { UpdatePasswordComponent } from './components/forms/update-password/update-password.component';
import { EditProfileComponent } from './components/forms/edit-profile/edit-profile.component';

//python -m uvicorn Fraud_prediction_api:app --reload
export const routes: Routes = [
    {
        path:'',
        component:FirstpageComponent
    },
    {
        path:'home',
        component:HomeComponent,
        canActivate:[isBannedgaurd,isLoggedguard,tokenExpguard]
    },
    {
        path:'register',
        component:RegisterComponent
    },
    {
        path:'changePassword',
        component:UpdatePasswordComponent,
        canActivate:[isBannedgaurd,isLoggedguard,tokenExpguard]
    },
    {
        path:'login',
        component:LoginComponent
    },
    {
        path:'profile',
        component:ProfileComponent,
        canActivate:[isBannedgaurd,isLoggedguard,tokenExpguard]
    },
     {
        path:'profile/edit',
        component:EditProfileComponent,
        canActivate:[isBannedgaurd,isLoggedguard,tokenExpguard]
    },
    {
      path:'accounts',
      component:AccountComponent,
      canActivate:[isBannedgaurd,isLoggedguard,tokenExpguard]
    },
    {
      path:'accounts/add',
      component:AccountRegisterComponent,
      canActivate:[isBannedgaurd,isLoggedguard,tokenExpguard]
    },
    {
      path:'transaction',
      component:TransactionComponent,
      canActivate:[isBannedgaurd,isLoggedguard,tokenExpguard]

    },
    {
      path:'transaction/history',
      component:TransactionHistoryComponent,
      canActivate:[isBannedgaurd,isLoggedguard,tokenExpguard]
    },
    {
        path:'adminDashboard',
        component:AdminDashboardComponent,
        canActivate:[isBannedgaurd,isLoggedguard,adminguard,tokenExpguard]
    },
    {
      path:'adminDashBoard/viewTransactions',
      component:ViewTransactionsComponent,
      canActivate:[isBannedgaurd,isLoggedguard,adminguard,tokenExpguard]
    },
    {
      path:'adminDashBoard/manageAccounts',
      component:ManageAccountsComponent,
     canActivate:[isBannedgaurd,isLoggedguard,adminguard,tokenExpguard]
    },
    {
      path:'adminDashBoard/manageUsers',
      component:ManageUsersComponent,
     canActivate:[isBannedgaurd,isLoggedguard,adminguard,tokenExpguard]
    },
    {
      path:'adminDashBoard/fraudRecords',
      component:FraudRecordsComponent,
      canActivate:[isBannedgaurd,isLoggedguard,adminguard,tokenExpguard]
    },
    {
      path:'adminDashBoard/fraudAlerts',
      component:FraudAlertsComponent,
      canActivate:[isBannedgaurd,isLoggedguard,adminguard,tokenExpguard]
    },
    {
      path:'adminDashBoard/deviceHistory',
      component:DeviceHistoryComponent,
      canActivate:[isBannedgaurd,isLoggedguard,adminguard,tokenExpguard]
    }



];
