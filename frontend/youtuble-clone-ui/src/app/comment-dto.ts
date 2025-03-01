export interface CommentDTO {
  id?: string;
  text: string;
  authorId: string;
  likes: number;
  dislikes: number;
  createdAt: string;
}
