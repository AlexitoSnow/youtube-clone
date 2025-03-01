import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgxFileDropEntry, FileSystemFileEntry, FileSystemDirectoryEntry, NgxFileDropModule } from 'ngx-file-drop';
import { MatButtonModule } from '@angular/material/button';
import { VideoService } from '../services/video.service';
import { Router } from '@angular/router';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'upload-video',
  templateUrl: './upload-video.component.html',
  styleUrls: ['./upload-video.component.css'],
  imports: [NgxFileDropModule, CommonModule, MatButtonModule],
})
export class UploadVideoComponent implements OnInit {
  public files: NgxFileDropEntry[] = [];
  fileUploaded: boolean = false;
  fileEntry: FileSystemFileEntry | undefined;

  constructor(
    private service: VideoService,
    private router: Router,
    private oidcSecurityService: OidcSecurityService
  ) {}
  ngOnInit(): void {
    this.oidcSecurityService.checkAuth().subscribe(({ isAuthenticated }) => {
      if (!isAuthenticated) {
        this.oidcSecurityService.authorize();
      }
    });
  }

  public videoDropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {
      if (droppedFile.fileEntry.isFile) {
        this.fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        this.fileEntry.file((file: File) => {
          this.fileUploaded = true;
        });
      } else {
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
      }
    }
  }

  public fileOver(event: any) {}

  public fileLeave(event: any) {}

  public uploadVideo() {
    if (this.fileEntry != undefined) {
      this.fileEntry.file((file) => {
        this.service.uploadVideo(file).subscribe((data) => {
          this.router.navigateByUrl('/video-details/' + data.id);
        });
      });
    }
  }
}
