export interface VideoDTO {
  id: string;
  title: string;
  description: string;
  userId: string;
  tags: Array<string>;
  videoUrl: string;
  videoStatus: string;
  thumbnailUrl: string;
}
