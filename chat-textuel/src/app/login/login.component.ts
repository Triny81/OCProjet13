import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';

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
  ) {}

  onSubmit() {
    this.loginService.login(this.email, this.password)
      .subscribe({
        next: (response) => {
          if (response.email == this.email) {
            this.router.navigate(['/chat']);
          } else {
            this.errorMessage = 'Identifiants incorrects';
          }
        },
        error: (err) => {
          this.errorMessage = '';
          console.error(err);
        }
      });
  }
}
