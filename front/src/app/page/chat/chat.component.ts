import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../../service/chat.service';
import { LoginService } from '../../service/login.service';
import { Message } from '../../interface/message.interface';
import { Conversation } from '../../interface/conversation.interface';
import { User } from '../../interface/user.interface';
import { ConversationRequest } from '../../interface/conversationRequest.interface';
import { MessageRequest } from '../../interface/messageRequest.interface';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {
  conversations: Conversation[] = [];
  selectedConversation!: Conversation;
  messages: Message[] = [];
  newMessage: string = '';
  currentUser!: User;
  private messageSubscription: Subscription | null = null;

  @ViewChild('messageList') messageList!: ElementRef;

  constructor(
    private chatService: ChatService,
    private loginService: LoginService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.currentUser = this.loginService.getCurrentUser();

    if (this.currentUser) {
      this.chatService.connect();
      this.loadConversations();
      this.messageSubscription = this.chatService.getMessageStream().subscribe((message: Message) => {
        if (message) {
          if (message.conversation.id === this.selectedConversation?.id) {
            this.messages.push(message);
            this.scrollToBottom();
          } else {
            const conv = this.conversations.find(c => c.id === message.conversation.id);
          }
        }
      });
    } else {
      this.router.navigate(['/login']);
    }
  }

  ngOnDestroy() {
    this.chatService.disconnect();
    this.messageSubscription?.unsubscribe();
  }

  loadConversations() {
    this.chatService.getConversationsByUser(this.currentUser.id).subscribe({
      next: (conversations: Conversation[]) => {
        this.conversations = conversations.map(conv => ({ ...conv, unreadCount: 0 }));
      },
      error: (err) => console.error('Erreur chargement conversations', err)
    });
  }

  selectConversation(conversation: Conversation) {
    this.selectedConversation = conversation;
    this.loadMessages(conversation.id);
    this.chatService.subscribeToConversation(conversation.id);
    this.scrollToBottom();
  }

  loadMessages(conversationId: number) {
    this.chatService.fetchMessages(conversationId).subscribe({
      next: (data) => {
        this.messages = data;
        this.scrollToBottom();
      },
      error: (err) => console.error('Erreur chargement messages', err)
    });
  }

  sendMessage() {
    if (this.newMessage.trim()) {
      const messageRequest: MessageRequest = {
        message: this.newMessage,
        authorId: this.currentUser.id,
        conversationId: this.selectedConversation.id
      };
      this.chatService.sendMessage(messageRequest);
      this.newMessage = '';
    }
  }

  createConversation() {
    const conversationRequest: ConversationRequest = { adminId: 1, clientId: this.currentUser.id };
    this.chatService.createConversation(conversationRequest).subscribe({
      next: (conversation: Conversation) => {
        this.conversations.push({ ...conversation});
      },
      error: (err) => console.error('Erreur creation conversation', err)
    });
  }

 scrollToBottom(): void {
    if (this.messageList) {
      setTimeout(() => {
        this.messageList.nativeElement.scrollTop = this.messageList.nativeElement.scrollHeight;
      }, 0);
    }
  }
}