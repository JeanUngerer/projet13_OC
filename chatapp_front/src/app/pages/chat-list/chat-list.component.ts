import {Component, OnInit} from '@angular/core';
import {ChatService} from '../../core/services/chat.service';
import {ChatMessagesComponent} from './chat-messages/chat-messages.component';
import {NgForOf, NgIf} from '@angular/common';
import {BehaviorSubject, Subject} from 'rxjs';

@Component({
  selector: 'app-chat-list',
  imports: [
    ChatMessagesComponent,
    NgIf,
    NgForOf
  ],
  templateUrl: './chat-list.component.html',
  standalone: true,
  styleUrl: './chat-list.component.scss'
})
export class ChatListComponent implements OnInit {
  chats: any[] = [];
  selectedChatId: number | null = null;
  selectedIdSubject: BehaviorSubject<number> = new BehaviorSubject(0);
  chatopened = false;
  displayClosed = false;

  constructor(private chatService: ChatService) { }

  ngOnInit(): void {
    const userId = 1; // Replace with actual logged-in user ID
    this.chatService.getMyChats(userId).subscribe(data => {
      this.chats = data;
    });

    this.chatService.connectWebSocket();
  }

  selectChat(chatId: number): void {
    this.displayClosed = false;
    this.selectedIdSubject.next(chatId)
    this.selectedChatId = chatId;

  }

  openChats(){
    this.chatService.getAllOpenChats().subscribe(data => {
      this.chats = data;
    });
    this.chatService.subscribeToNewChats((chat: any) => {
      this.chats.push(chat);
      console.log("Adding : ", chat)
    });
    this.chatService.subscribeToCloseChats((chat: any) => {
      const index = this.chats.findIndex((c: any) => c.id === chat.id);
      if (index !== -1) {
        console.log("Closing : ", chat)
        this.chats.splice(index, 1);
        if(chat.id === this.selectedChatId){
          this.displayClosed = true;
        }
      }
    });
  }
}
