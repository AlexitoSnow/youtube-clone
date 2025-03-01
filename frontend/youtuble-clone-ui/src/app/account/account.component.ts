import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-account',
  imports: [MatButtonModule],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css',
})
export class AccountComponent implements OnInit {
  constructor(private oidcSecurityService: OidcSecurityService, private service: UserService) {}

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({ isAuthenticated }) => {
        if (!isAuthenticated) {
          this.oidcSecurityService.authorize();
        }
      }
    );
  }

  logout() {
    this.oidcSecurityService
      .logoffAndRevokeTokens()
      .subscribe((result) => {
        this.service.userLogged = undefined;
      });
    this.oidcSecurityService.logoffLocal();
  }
}
