import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Message } from '../interface/message.interface';
import { Conversation } from '../interface/conversation.interface';
import { ConversationRequest } from '../interface/conversationRequest.interface';
import { MessageRequest } from '../interface/messageRequest.interface';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private apiUrl = 'http://localhost:3001/api';

  constructor(private http: HttpClient) {}

  getConversationsByUser(userId: number): Observable<Conversation[]> {
    return this.http.get<Conversation[]>(`${this.apiUrl}/conversations/user/${userId}`);
  }

  createConversation(conversationRequest: ConversationRequest): Observable<Conversation> {
    return this.http.post<Conversation>(`${this.apiUrl}/conversations`, conversationRequest);
  }

  getMessages(conversationId: number): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/messages/conversation/${conversationId}`);
  }

  sendMessage(messageRequest: MessageRequest): Observable<Message> {
    return this.http.post<Message>(`${this.apiUrl}/messages`, messageRequest);
  }
}
