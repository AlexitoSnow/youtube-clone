import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors, } from '@angular/common/http';
import {
  authInterceptor,
  LogLevel,
  provideAuth,
} from 'angular-auth-oidc-client';
import { CsrfInterceptor } from './csrf-interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor(), CsrfInterceptor])),
    provideAuth({
      config: {
        authority: 'https://dev-apaatbftgixmzn3z.us.auth0.com',
        redirectUrl: window.location.origin,
        postLogoutRedirectUri: window.location.origin,
        clientId: 'dlL796i3m4udHhRAo5GS7CDeH7qrK2yY',
        scope: 'openid profile email offline_access',
        responseType: 'code',
        silentRenew: true,
        useRefreshToken: true,
        logLevel: LogLevel.Debug,
        secureRoutes: ['http://localhost:8080'],
        customParamsAuthRequest: {
          audience: 'http://localhost:8080',
        },
      },
    }),
  ],
};
