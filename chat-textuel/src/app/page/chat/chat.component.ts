import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../../service/chat.service';
import { LoginService } from '../../service/login.service';
import { Message } from '../../interface/message.interface';
import { Conversation } from '../../interface/conversation.interface';
import { User } from '../../interface/user.interface';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent implements OnInit {
  conversations: Conversation[] = [];
  selectedConversation!: Conversation;
  messages: Message[] = [];
  newMessage: string = '';
  currentUser!: User;

  constructor(
    private chatService: ChatService,
    private loginService: LoginService) { }

  ngOnInit() {
    this.currentUser = this.loginService.getCurrentUser();
    if (this.currentUser) {
      this.loadConversations();
    } else {
      console.error('Aucun utilisateur connecté');
    }
  }

  loadConversations() {
    this.chatService.getConversations(this.currentUser.id).subscribe({
      next: (data: Conversation[]) => {
        this.conversations = data;

        if (this.conversations.length === 0 && this.currentUser.role.id === 1) { 

        } 
        
      },
      error: (err) => console.error('Erreur chargement conversations', err)
    });
  }

  selectConversation(conversation: any) {
    this.selectedConversation = conversation;
    this.loadMessages(conversation.id);
  }

  loadMessages(conversationId: number) {
    this.chatService.getMessages(conversationId).subscribe({
      next: (data) => {

        this.messages = data;
      },
      error: (err) => console.error('Erreur chargement messages', err)
    });
  }

  sendMessage() {
    if (this.newMessage.trim() && this.selectedConversation) {
      const message: Message = {
        message: this.newMessage,
        author: this.currentUser,
        conversation: this.selectedConversation
      };
      this.chatService.sendMessage(message).subscribe({
        next: (response) => {
          this.messages.push(response);
          this.newMessage = '';
        },
        error: (err) => console.error('Erreur envoi message', err)
      });
    }
  }

  createConversation() {
    this.chatService.createConversation(this.currentUser.id).subscribe({
      next: (response) => {
    
      },
      error: (err) => console.error('Erreur creation conversation', err)
    });
  }
}
