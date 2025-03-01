import { Component } from '@angular/core';
import { VideoDTO } from '../video-dto';
import { CommonModule } from '@angular/common';
import { VideoService } from '../services/video.service';
import { VideoCardComponent } from "../video-card/video-card.component";
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home-page',
  imports: [VideoCardComponent, CommonModule],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css',
})
export class HomePageComponent {
  videos: VideoDTO[] = [];

  public constructor(
    private videoService: VideoService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private router: Router
  ) {
    const auth = activatedRoute.snapshot.queryParamMap.get('auth');
    if (auth) {
      this.userService.registerUser();
      router.navigateByUrl('/');
    }
    videoService.retrieveVideos().subscribe((data) => {
      this.videos = data;
    });
  }
}
