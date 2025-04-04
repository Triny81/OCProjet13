import { Role } from "./role.interface";

export interface User {
    id: number;
    name: string;
    firstName: string;
    email: string;
    password: string;
    role: Role;
    createdAt: string;
    updatedAt: string;
  }