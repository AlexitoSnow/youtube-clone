import { Routes } from '@angular/router';
import { UploadVideoComponent } from './upload-video/upload-video.component';
import { AccountComponent } from './account/account.component';
import { SaveVideoDetailsComponent } from './save-video-details/save-video-details.component';
import { VideoPageComponent } from './video-page/video-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { SubscriptionsComponent } from './subscriptions/subscriptions.component';
import { HistoryComponent } from './history/history.component';
import { LikedVideosComponent } from './liked-videos/liked-videos.component';

export const routes: Routes = [
  {
    path: '',
    title: 'Youtuble Clone',
    component: HomePageComponent,
  },
  {
    path: 'upload-video',
    title: 'Upload your video',
    component: UploadVideoComponent,
  },
  {
    path: 'account',
    title: 'Account',
    component: AccountComponent,
  },
  {
    path: 'video-details/:videoId',
    component: SaveVideoDetailsComponent,
    title: 'Video Details',
  },
  {
    path: 'watch',
    title: 'Video Page',
    component: VideoPageComponent,
  },
  {
    path: 'subscriptions',
    title: 'My Subscriptions',
    component: SubscriptionsComponent,
  },
  {
    path: 'history',
    title: 'Video History',
    component: HistoryComponent,
  },
  {
    path: 'liked-videos',
    title: 'My Liked Videos',
    component: LikedVideosComponent,
  },
];
