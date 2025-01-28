import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, Subscription} from 'rxjs';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client/dist/sockjs';
import {JWTService} from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiBaseUrl = 'http://localhost:8087/api/chats'; // API backend URL
  private stompClient: any;
  public customerChatId?: number;
  msg: string[] = [];
  constructor(
    private http: HttpClient,
    private jwtService: JWTService,
  ) {

  }

  // Fetch all chats for a user
  getMyChats(userId: number): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/mychats`);
  }

  getMyChatId(chatId: number): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/mychat/${chatId}`);
  }

  getAllOpenChats(): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/openchats`);
  }

  newChat(){
    return this.http.post(`${this.apiBaseUrl}/newchat`, {});
  }

  closeChat(chatId: number){
    console.log("Send close")
    return this.http.put(`${this.apiBaseUrl}/close/${chatId}`, {});
  }

  // Send a new message to a chat
  sendMessage(chatId: number, content: string, sentByUser: boolean): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/${chatId}/messages`, { content });
  }

  // Initialize WebSocket connection
  connectWebSocket(): void {
    const socket = new SockJS('http://localhost:8087/api/chat-websocket');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({'X-Authorization': 'Bearer ' + this.jwtService.getToken()}, () => {
      console.log('Connected to WebSocket');
    });
  }

  // Disconnect WebSocket connection
  disconnectWebSocket(): void {
    if (this.stompClient) {
      this.stompClient.disconnect(() => {
        console.log('Disconnected from WebSocket');
      });
    }
  }

  subscribeToNewChats(callback: (chat: any) => void): void {
    if (this.stompClient) {
      console.log("Sub All Open")
      this.stompClient.subscribe(`/topic/new-chat`, (chat: any) => {
        callback(JSON.parse(chat.body));
        console.log("new : ", chat.body)
      });
    }
  }

  subscribeToCloseChats(callback: (chat: any) => void): void {
    if (this.stompClient) {
      console.log("Sub All Close")
      this.stompClient.subscribe(`/topic/close-chat`, (chat: any) => {
        callback(JSON.parse(chat.body));
        console.log("close : ", chat.body)
      });
    }
  }

  // Subscribe to a specific chat for real-time messages
  subscribeToChat(chatId: number, callback: (message: any) => void): Subscription | null {
    if (this.stompClient) {
      return this.stompClient.subscribe(`/topic/messages/${chatId}`, (message: any) => {
        callback(JSON.parse(message.body));
        console.log("message : ", message.body)
      });
    }
    return null;
  }
}
