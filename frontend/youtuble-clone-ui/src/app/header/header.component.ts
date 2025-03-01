import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { CommonModule } from '@angular/common';
import { UserService } from '../services/user.service';
import { UserDTO } from '../user-dto';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'tool-bar',
  templateUrl: 'header.component.html',
  styleUrl: 'header.component.css',
  imports: [
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    RouterLink,
    CommonModule,
    MatMenuModule,
  ],
})
export class ToolBarHeaderComponent implements OnInit {
  isAuthenticated: boolean = false;
  user: UserDTO | null = null;

  constructor(
    private oidcSecurityService: OidcSecurityService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.oidcSecurityService.checkAuth().subscribe(({ isAuthenticated }) => {
      this.isAuthenticated = isAuthenticated;
    });
  }

  logout() {
    this.oidcSecurityService.logoffAndRevokeTokens().subscribe((result) => {
      this.userService.userLogged = undefined;
    });
    this.oidcSecurityService.logoffLocal();
  }

  login() {
    console.log(
      'use this to login',
      'alexitosnow2002@gmail.com',
      'Alexito123-'
    );
    this.oidcSecurityService.authorize();
  }
}
