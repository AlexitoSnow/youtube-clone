import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { VideoDTO } from '../video-dto';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { VideoService } from '../video.service';

@Component({
  selector: 'app-home-page',
  imports: [MatCardModule, MatButtonModule, RouterLink, CommonModule],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css',
})
export class HomePageComponent {
  videos: VideoDTO[] = [];

  public constructor(private service: VideoService) {
    service.retrieveVideos().subscribe(data => {
      this.videos = data;
      console.log(data);
    });
  }

}
