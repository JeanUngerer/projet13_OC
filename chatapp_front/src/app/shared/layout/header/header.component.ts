import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {MatToolbar} from '@angular/material/toolbar';
import {NgOptimizedImage} from '@angular/common';
import {AuthService} from '../../../core/services/auth.service';
import {ChatService} from '../../../core/services/chat.service';

@Component({
  selector: 'app-header',
  imports: [
    RouterOutlet,
    MatToolbar,
    RouterLink,
    NgOptimizedImage,
  ],
  templateUrl: './header.component.html',
  standalone: true,
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  constructor(
    private authService: AuthService,
    private chatService: ChatService,
  ) {
  }

  logout(){
    if (this.chatService.customerChatId) {
      this.chatService.closeChat(this.chatService.customerChatId).subscribe();
      console.log('Logout Closing')
    }
    console.log('LOGOUT')
    this.authService.logout();
  }
}
