import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { HttpErrorResponse, HttpEvent, HttpHandlerFn, HttpRequest, provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app/app.routes';
import { LoginService } from './app/service/login.service';
import { catchError, Observable, throwError } from 'rxjs';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor])),
    LoginService,
  ]
}).catch(err => console.error(err));

export function authInterceptor(req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> {
  console.log('Interceptor exécuté pour :', req.url);
  const clonedReq = req.clone({ withCredentials: true });
  return next(clonedReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 || error.status === 403) {
        window.location.href = '/';
      }
      return throwError(() => error);
    })
  );
}
