import { Conversation } from "./conversation.interface";
import { User } from "./user.interface";

export interface Message {
    id: number;
    message: string;
    author: User;
    admin: User;
    conversation: Conversation;
    createdAt: string;
    updatedAt: string;
}