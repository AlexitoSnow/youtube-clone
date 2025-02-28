import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterLink } from '@angular/router';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { CommonModule } from '@angular/common';

/**
 * @title Toolbar overview
 */
@Component({
  selector: 'tool-bar',
  templateUrl: 'header.component.html',
  styleUrl: 'header.component.css',
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, RouterLink, CommonModule],
})
export class ToolBarHeaderComponent implements OnInit {
  isAuthenticated: boolean = false;

  constructor(private oidcSecurityService: OidcSecurityService) {}

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({ isAuthenticated }) => {
        this.isAuthenticated = isAuthenticated;
      }
    );
  }

  login() {
    console.log('use this to login', 'alexitosnow2002@gmail.com', 'Alexito123-');
    this.oidcSecurityService.authorize();
  }
}
