import { Routes } from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {NoAuthGuard} from './core/guards/unauth.guard';
import {LoginComponent} from './pages/login/login.component';
import {AuthGuard} from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login', component: LoginComponent, title: "Login | YourCar YourWay", canActivate: [NoAuthGuard]
  },
  {
    path: '', component: HomeComponent, title: "Home | YourCar YourWay", canActivate: [AuthGuard]
  },
];
