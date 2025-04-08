import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Message } from '../interface/message.interface';
import { Conversation } from '../interface/conversation.interface';
import { ConversationRequest } from '../interface/conversationRequest.interface';
import { MessageRequest } from '../interface/messageRequest.interface';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = 'http://localhost:3001/api';
  private stompClient: Client | null = null;
  private messageSubject = new Subject<Message>();

  constructor(private http: HttpClient) { }

  connect(): void {
    if (this.stompClient?.active) {
      return;
    }

    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:3001/ws-chat'),
      reconnectDelay: 5000
    });

    this.stompClient.onConnect = (frame) => {
    };

    this.stompClient.onStompError = (frame) => {
      console.error('Erreur STOMP : ' + frame);
    };

    this.stompClient.activate();
  }

  subscribeToConversation(conversationId: number): void {
    if (this.stompClient?.active) {
      this.stompClient.subscribe(`/topic/conversation/${conversationId}`, (message) => {
        if (message.body) {
          const newMessage: Message = JSON.parse(message.body);
          this.messageSubject.next(newMessage);
        }
      });
    } else {
      console.error('STOMP client non connecté pour la souscription');
    }
  }

  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.deactivate();
      this.stompClient = null;
    }
  }

  getConversationsByUser(userId: number): Observable<Conversation[]> {
    return this.http.get<Conversation[]>(`${this.apiUrl}/conversations/user/${userId}`);
  }

  createConversation(conversationRequest: ConversationRequest): Observable<Conversation> {
    return this.http.post<Conversation>(`${this.apiUrl}/conversations`, conversationRequest);
  }

  fetchMessages(conversationId: number): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/messages/conversation/${conversationId}`);
  }

  sendMessageAsync(messageRequest: MessageRequest): Observable<Message> {
    return this.http.post<Message>(`${this.apiUrl}/messages`, messageRequest);
  }

  sendMessage(messageRequest: MessageRequest): void {
    if (!this.stompClient || !this.stompClient.active) {
      console.error('STOMP client non connecté ou non initialisé');
      return;
    }
    this.stompClient.publish({
      destination: '/app/chat.send',
      body: JSON.stringify(messageRequest)
    });
  }

  getMessageStream(): Observable<Message> {
    return this.messageSubject.asObservable();
  }
}
