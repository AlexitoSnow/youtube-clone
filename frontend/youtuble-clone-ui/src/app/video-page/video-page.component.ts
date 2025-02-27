import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { VideoService } from '../video.service';
import { VideoDTO } from '../video-dto';
import { VideoPlayerComponent } from "../video-player/video-player.component";
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'watch-page',
  imports: [VideoPlayerComponent, MatIconButton, MatIcon, CommonModule],
  templateUrl: './video-page.component.html',
  styleUrl: './video-page.component.css',
})
export class VideoPageComponent {
  videoId: string = '';
  videoDetails: VideoDTO | undefined;
  videoUrl: string = '';

  constructor(private route: ActivatedRoute, private service: VideoService, private _snackBar: MatSnackBar) {
    this.route.queryParams.subscribe((params) => {
      this.videoId = params['v'];

      this.service.getVideo(this.videoId!).subscribe((video) => {
        document.title = video.title;
        this.videoDetails = video;
        this.videoUrl = video.videoUrl;
      });
    });
  }

  likeVideo($event: MouseEvent) {
    this._snackBar.open("Added to your liked videos", "Ok");
  }
}
