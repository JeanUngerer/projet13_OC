import {Component, OnInit} from '@angular/core';
import {ChatListComponent} from '../chat-list/chat-list.component';
import {ChatService} from '../../core/services/chat.service';
import {AuthService} from '../../core/services/auth.service';
import {MyChatsComponent} from '../my-chats/my-chats.component';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [
    ChatListComponent,
    MyChatsComponent,
    NgIf
  ],
  templateUrl: './home.component.html',
  standalone: true,
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{

  isCustomer = true;
  constructor(
    private chatService: ChatService,
    private authService: AuthService,
  ) {
  }

  newChat(){
    this.chatService.newChat()
      .subscribe(res => console.log("RES :", res))
  }

  ngOnInit(): void {
    this.isCustomer = this.authService.getIsCustomer();
  }
}
