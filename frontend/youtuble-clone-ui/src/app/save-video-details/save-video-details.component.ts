import { COMMA, ENTER, SPACE } from '@angular/cdk/keycodes';
import { Component, inject, ViewChild, ElementRef } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { MatDialogTitle } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { VideoService } from '../services/video.service';
import { CommonModule } from '@angular/common';
import { VideoPlayerComponent } from '../video-player/video-player.component';
import { VideoDTO } from '../video-dto';

@Component({
  selector: 'video-details',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDialogTitle,
    ReactiveFormsModule,
    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    CommonModule,
    VideoPlayerComponent,
  ],
  templateUrl: './save-video-details.component.html',
  styleUrl: './save-video-details.component.css',
})
export class SaveVideoDetailsComponent {
  private _snackBar = inject(MatSnackBar);
  readonly addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA, SPACE] as const;
  videoDetailsForm: FormGroup;
  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('');
  status: FormControl = new FormControl('');
  readonly tags: string[] = [];
  selectedFile!: File;
  selectedFileName: string = 'Nothing Selected';
  videoId = '';
  videoDetails: VideoDTO | undefined;
  imageUrl = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private service: VideoService,
    private router: Router
  ) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    this.videoDetailsForm = new FormGroup({
      title: this.title,
      description: this.description,
      videoStatus: this.status,
    });
    service.getVideo(this.videoId).subscribe((data) => {
      this.videoDetails = data;
    });
  }

  saveVideoDetails($event: MouseEvent) {
    this.videoDetails!.description = this.description.value;
    this.videoDetails!.tags = this.tags;
    this.videoDetails!.title = this.title.value;
    this.videoDetails!.videoStatus = this.status.value;

    this.updateVideoDetails();
  }

  updateVideoDetails() {
    this.service.updateVideoDetails(this.videoDetails!).subscribe((data) => {
      this._snackBar.open('Video Updated', 'Ok');
      this.router.navigateByUrl(`/watch?v=${this.videoId}`);
    });
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim().toLocaleLowerCase();

    if (value && this.tags.indexOf(value) < 0) {
      this.tags.push(value);
    }
    event.chipInput!.clear();
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  onFileSelected(input: HTMLInputElement) {
    if (input.files && input.files.length) {
      this.selectedFile = input.files[0];
      this.selectedFileName = this.selectedFile.name;
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.imageUrl = event.target.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  onUploadThumbnail() {
    this.service
      .uploadThumbnail(this.selectedFile, this.videoId)
      .subscribe((data) => {
        this.videoDetails!.thumbnailUrl = data.url;
        this._snackBar.open('Thumbnail applied!', 'Ok');
        this.imageUrl = '';
      });
  }
}
