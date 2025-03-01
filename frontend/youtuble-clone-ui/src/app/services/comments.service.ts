import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommentDTO } from '../comment-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  commentsUrl = 'http://localhost:8080/api/videos';

  constructor(private httpClient: HttpClient) { }

  getComments(videoId: string, comment: Array<CommentDTO>): Observable<Array<CommentDTO>> {
    return this.httpClient.get<Array<CommentDTO>>(`${this.commentsUrl}/${videoId}/comments`);
  }

  addComment(videoId: string, comment: CommentDTO): Observable<any> {
    return this.httpClient.post<any>(`${this.commentsUrl}/${videoId}/comments`, comment);
  }
}
