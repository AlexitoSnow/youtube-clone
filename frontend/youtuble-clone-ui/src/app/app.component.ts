import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ToolBarHeaderComponent } from "./header/header.component";
import { SidebarComponent } from "./sidebar/sidebar.component";
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    ToolBarHeaderComponent,
    SidebarComponent,
    MatSidenavModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
}
