import { Routes } from '@angular/router';
import { ChatComponent } from './page/chat/chat.component';
import { LoginComponent } from './page/login/login.component';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'chat', component: ChatComponent}
];
