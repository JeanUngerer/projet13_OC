import {Component, Input, OnInit} from '@angular/core';
import {ChatService} from '../../../core/services/chat.service';
import {DatePipe, NgForOf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {BehaviorSubject, Subject, Subscription} from 'rxjs';

@Component({
  selector: 'app-chat-messages',
  imports: [
    NgForOf,
    FormsModule,
    DatePipe
  ],
  templateUrl: './chat-messages.component.html',
  standalone: true,
  styleUrl: './chat-messages.component.scss'
})
export class ChatMessagesComponent implements OnInit {
  @Input() chatIdSubject: BehaviorSubject<number> = new BehaviorSubject(0)
  chatId = 0
  @Input() chats!: any[];
  messages: any[] = [];
  newMessage: string = '';

  chat!: any; // Declare variable for filtered chat

  subscription: Subscription | null = null;

  constructor(private chatService: ChatService) { }

  ngOnInit(): void {
    this.chatIdSubject.subscribe(id => {
      this.chatId = id;
      this.init()
    })
    //this.init()
  }

  init(){
    if(this.subscription){
      this.subscription.unsubscribe();
    }
    this.chatService.getMyChatId(this.chatId)
      .subscribe(data => {
        this.chat = data;
        this.messages = this.chat.messages;
      });
    this.subscription = this.chatService.subscribeToChat(this.chatId, (message) => {
      this.messages.push(message);
      console.log("New message : ", message)
    });
  }
  sendMessage(): void {
    if (this.newMessage.trim()) {
      this.chatService.sendMessage(this.chatId, this.newMessage, true).subscribe(response => {
        this.newMessage = ''; // Clear input field
      });
    }
  }
}
