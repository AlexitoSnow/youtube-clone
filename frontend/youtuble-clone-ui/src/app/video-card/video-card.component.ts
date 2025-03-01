import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { VideoDTO } from '../video-dto';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';
import { UserService } from '../services/user.service';
import { MatIconModule } from '@angular/material/icon';
import { UserDisplay } from '../user-display';

@Component({
  selector: 'app-video-card',
  imports: [
    MatCardModule,
    MatButtonModule,
    RouterLink,
    CommonModule,
    MatIconModule,
  ],
  templateUrl: './video-card.component.html',
  styleUrl: './video-card.component.css',
})
export class VideoCardComponent implements OnChanges {
  @Input()
  video!: VideoDTO;
  user!: UserDisplay;

  public constructor(private userService: UserService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['video'] && this.video) {
      this.getUserDetails(this.video.userId);
    }
  }

  public getUserDetails(userId: string) {
    this.userService.retrieveUser(userId).subscribe((data) => {
      this.user = data;
    });
  }
}
