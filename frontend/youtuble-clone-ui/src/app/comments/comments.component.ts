import { Component, Input, OnInit } from '@angular/core';
import { CommentDTO } from '../comment-dto';
import { UserService } from '../services/user.service';
import { UserDisplay } from '../user-display';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommentsService } from '../services/comments.service';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'comments-section',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css'],
  imports: [
    MatIconModule,
    MatFormFieldModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule
  ],
})
export class CommentsComponent implements OnInit {
  comments: CommentDTO[] = [];
  count: number = 0;
  @Input() videoId: string = '';
  @Input() isAuth: boolean = false;
  commentsForm: FormGroup;

  constructor(private userService: UserService, private commentsService: CommentsService) {
    this.commentsForm = new FormGroup({
      comment: new FormControl(),
    });
  }

  ngOnInit(): void {
    this.fetchComments();
  }

  fetchComments(): void {
    this.commentsService.getComments(this.videoId, this.comments).subscribe((data) => {
      this.comments = data;
      this.count = this.comments.length;
    });
  }

  addComment(): void {
    if (this.isAuth){
      const comment = this.commentsForm.get('comment')?.value
      const newComment: CommentDTO = {
        text: comment,
        authorId: this.userService.userLogged!.id,
        likes: 0,
        dislikes: 0,
        createdAt: new Date().toISOString(),
      };
      this.commentsService.addComment(this.videoId, newComment).subscribe(
        () => {
          this.fetchComments();
          this.commentsForm.reset();
        }
      );

    } else {
      alert('You need to be logged in to comment');
    }
  }

  getUserDisplay(userId: string): UserDisplay {
    let userDisplay: UserDisplay = {
      userId: '',
      displayName: '',
      picture: '',
      subscribers: 0,
    };
    this.userService.retrieveUser(userId).subscribe((data) => {
      userDisplay = data;
    });
    return userDisplay;
  }
}
