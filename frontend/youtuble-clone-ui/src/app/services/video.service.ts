import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { UploadResponse as UploadResponse } from '../upload-video/UploadVideoResponse';
import { VideoDTO } from '../video-dto';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Injectable({
  providedIn: 'root',
})
export class VideoService {
  videosUrl = 'http://localhost:8080/api/videos';
  constructor(
    private httpClient: HttpClient,
  ) {}

  uploadVideo(file: File): Observable<UploadResponse> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.httpClient.post<UploadResponse>(
      this.videosUrl + '/upload',
      formData
    );
  }

  uploadThumbnail(file: File, videoId: string): Observable<UploadResponse> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.httpClient.post<UploadResponse>(
      `${this.videosUrl}/upload/${videoId}/thumbnail`,
      formData
    );
  }

  getVideo(videoId: string): Observable<VideoDTO> {
    return this.httpClient.get<VideoDTO>(`${this.videosUrl}/${videoId}`);
  }

  updateVideoDetails(videoDetails: VideoDTO): Observable<VideoDTO> {
    return this.httpClient.put<VideoDTO>(this.videosUrl, videoDetails, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }

  retrieveVideos(): Observable<Array<VideoDTO>> {
    return this.httpClient.get<Array<VideoDTO>>(this.videosUrl);
  }

  likeVideo(videoId: string): Observable<VideoDTO> {
    return this.httpClient.put<VideoDTO>(`${this.videosUrl}/${videoId}/like`, {});
  }

  dislikeVideo(videoId: string): Observable<VideoDTO> {
    return this.httpClient.put<VideoDTO>(`${this.videosUrl}/${videoId}/dislike`, {});
  }
}
