import {
  HttpEvent,
  HttpRequest,
  HttpHandlerFn,
  HttpInterceptorFn,
} from '@angular/common/http';
import { Observable } from 'rxjs';

export const CsrfInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<HttpEvent<any>> => {
  const csrfToken = getCsrfToken();
  if (csrfToken) {
    const cloned = req.clone({
      headers: req.headers.set('X-CSRF-TOKEN', csrfToken),
    });
    return next(cloned);
  } else {
    return next(req);
  }
};

function getCsrfToken(): string | null {
  // Implementa la lógica para obtener el token CSRF desde el almacenamiento local o las cookies
  return localStorage.getItem('X-CSRF-TOKEN');
}
