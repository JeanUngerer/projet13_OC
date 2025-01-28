import { Component } from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {HeaderComponent} from './shared/layout/header/header.component';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, NgIf],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'YourCar-YourWay';

  constructor(public router: Router) {
  }
}
