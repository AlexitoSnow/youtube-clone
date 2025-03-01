import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../services/video.service';
import { VideoDTO } from '../video-dto';
import { VideoPlayerComponent } from "../video-player/video-player.component";
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { UserService } from '../services/user.service';
import { MatCardModule } from '@angular/material/card';
import { UserDisplay } from '../user-display';
import { CommentsComponent } from "../comments/comments.component";

@Component({
  selector: 'watch-page',
  imports: [
    VideoPlayerComponent,
    MatButtonModule,
    MatIconModule,
    MatButtonToggleModule,
    CommonModule,
    MatCardModule,
    CommentsComponent
],
  templateUrl: './video-page.component.html',
  styleUrl: './video-page.component.css',
})
export class VideoPageComponent {
  videoId: string = '';
  videoDetails: VideoDTO | undefined;
  videoUrl: string = '';
  userDetails?: UserDisplay;
  isSubscribed: boolean = false;
  likeButton: string = 'thumb_up_off_alt';
  dislikeButton: string = 'thumb_down_off_alt';

  constructor(
    private route: ActivatedRoute,
    private videoService: VideoService,
    private userService: UserService,
    private _snackBar: MatSnackBar,
    private oidcSecurityService: OidcSecurityService
  ) {
    this.route.queryParams.subscribe((params) => {
      this.videoId = params['v'];

      this.fetchVideoData();
    });
  }

  toggleSubscription($event: MouseEvent) {
    if (this.isAuthenticated()) {
      if (this.isSubscribed) {
        this.userService.unsubscribeUser(this.videoDetails!.userId).subscribe((response) => {
          this.isSubscribed = false;
        });
      } else {
        this.userService.subscribeUser(this.videoDetails!.userId).subscribe((response) => {
          this.isSubscribed = true;
        });
      }
      this.getUserDetails(this.videoId);
    } else {
      this._snackBar.open('You must be logged in to subscribe to a user', 'Ok');
    }
  }

  private fetchVideoData() {
    this.videoService.getVideo(this.videoId!).subscribe((video) => {
      document.title = video.title;
      this.videoDetails = video;
      this.videoUrl = video.videoUrl;
      this.getUserDetails(video.userId);
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

  public getUserDetails(userId: string) {
    this.userService.retrieveUser(userId).subscribe((data) => {
      this.userDetails = data;
    });
  }

  likeVideo($event: MouseEvent) {
    if (this.isAuthenticated()) {
      this.videoService.likeVideo(this.videoId!).subscribe((video) => {
        let message = 'Added to your liked videos';
        this.likeButton = 'thumb_up';
        this.dislikeButton = 'thumb_down_off_alt';
        if (this.videoDetails!.likes > video.likes) {
          message = 'Removed from your liked videos';
          this.likeButton = 'thumb_up_off_alt';
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
      this.videoService.dislikeVideo(this.videoId!).subscribe((video) => {
        let message = 'Added to your disliked videos';
        this.dislikeButton = 'thumb_down';
        this.likeButton = 'thumb_up_off_alt';
        if (this.videoDetails!.dislikes > video.dislikes) {
          message = 'Removed from your disliked videos';
          this.dislikeButton = 'thumb_down_off_alt';
        }
        this.videoDetails = video;
        this._snackBar.open(message, 'Ok');
      });
    } else {
      this._snackBar.open('You must be logged in to like a video', 'Ok');
    }
  }
}
