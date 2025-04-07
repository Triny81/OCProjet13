import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../service/login.service';
import { LoginRequest } from '../../interface/loginRequest.interface';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(
    private loginService: LoginService,
    private router: Router
  ) { }

  onSubmit() {
    const loginRequest: LoginRequest = { email: this.email, password: this.password };

    this.loginService.login(loginRequest).subscribe({
      next: (user) => {
        console.log('Utilisateur connecté', user);
        this.router.navigate(['/chat']);
      },
      error: (err) => {
        console.error('Erreur de connexion', err);
        this.errorMessage = 'Identifiants incorrects ou problème serveur';
      }
    });
  }
}
