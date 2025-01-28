import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChatService} from '../../core/services/chat.service';
import {ChatMessagesComponent} from '../chat-list/chat-messages/chat-messages.component';
import {NgForOf, NgIf} from '@angular/common';
import {BehaviorSubject, Subject} from 'rxjs';

@Component({
  selector: 'app-my-chats',
  imports: [
    ChatMessagesComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './my-chats.component.html',
  standalone: true,
  styleUrl: './my-chats.component.scss'
})
export class MyChatsComponent implements OnInit, OnDestroy{

  chats: any[] = [];
  oldChats: any[] = [];
  selectedChatId: number | null = null;
  selectedIdSubject: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor(private chatService: ChatService) { }

  ngOnInit(): void {
    const userId = 1; // Replace with actual logged-in user ID
    this.chatService.getMyChats(userId).subscribe(data => {
      this.oldChats = data;
    });

    this.chatService.connectWebSocket();

  }

  selectChat(chatId: number): void {
    console.log(chatId)
    this.selectedIdSubject.next(chatId)
    this.selectedChatId = chatId;
  }

  newChat(){
    this.chatService.newChat()
      .subscribe((res: any) => {
        console.log("RES :", res);
        this.chats.push(res);
        this.chatService.customerChatId = res.id;
        this.selectChat(res.id)
      })
  }

  ngOnDestroy(): void {
    console.log("Destroy")
    if (this.selectedIdSubject) {
      this.selectedIdSubject.unsubscribe();
    }
  }
}
