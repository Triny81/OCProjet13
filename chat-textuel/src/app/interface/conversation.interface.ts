import { User } from "./user.interface";

export interface Conversation {
    id: number;
    adminId: number;
    clientId: number;
    admin: User;
    client: User;
    createdAt?: string;
    updatedAt?: string;
  }