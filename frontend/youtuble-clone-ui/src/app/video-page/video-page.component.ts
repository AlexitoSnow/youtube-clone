import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';
import { VideoDTO } from '../video-dto';
import { VideoPlayerComponent } from "../video-player/video-player.component";
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'watch-page',
  imports: [
    VideoPlayerComponent,
    MatButtonModule,
    MatIconModule,
    MatButtonToggleModule,
    CommonModule,
  ],
  templateUrl: './video-page.component.html',
  styleUrl: './video-page.component.css',
})
export class VideoPageComponent {
  videoId: string = '';
  videoDetails: VideoDTO | undefined;
  videoUrl: string = '';

  constructor(
    private route: ActivatedRoute,
    private service: VideoService,
    private _snackBar: MatSnackBar,
    private oidcSecurityService: OidcSecurityService
  ) {
    this.route.queryParams.subscribe((params) => {
      this.videoId = params['v'];

      this.service.getVideo(this.videoId!).subscribe((video) => {
        document.title = video.title;
        this.videoDetails = video;
        this.videoUrl = video.videoUrl;
      });
    });
  }

  isAuthenticated() {
    let canInteract = false;
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({ isAuthenticated }) => {
        canInteract = isAuthenticated;
      }
    );
    return canInteract;
  }

  likeVideo($event: MouseEvent) {
    if (this.isAuthenticated()) {
      this.service.likeVideo(this.videoId!).subscribe((video) => {
        let message = 'Added to your liked videos';
        if (this.videoDetails!.likes > video.likes) {
          message = 'Removed from your liked videos';
        }
        this.videoDetails = video;
        this._snackBar.open(message, 'Ok');
      });
    } else {
      this._snackBar.open('You must be logged in to like a video', 'Ok');
    }
  }

  dislikeVideo($event: MouseEvent) {
    if (this.isAuthenticated()) {
      this.service.dislikeVideo(this.videoId!).subscribe((video) => {
        let message = 'Added to your disliked videos';
        if (this.videoDetails!.dislikes > video.dislikes) {
          message = 'Removed from your disliked videos';
        }
        this.videoDetails = video;
        this._snackBar.open(message, 'Ok');
      });
    } else {
      this._snackBar.open('You must be logged in to like a video', 'Ok');
    }
  }
}
