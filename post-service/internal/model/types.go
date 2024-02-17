package model

type AddCommentRequest struct {
	AuthorID uint64 `json:"authorId"`
	Content  string `json:"content"`
	PostID   uint64 `json:"postId"`
}

type AddPostRequest struct {
	Content  string   `json:"content"`
	Type     PostType `json:"type"`
	AuthorID uint64   `json:"authorId"`
}

type UpdatePostRequest struct {
	Content string `json:"content"`
}

type UpdateCommentRequest struct {
	Content string `json:"content"`
}

type AddPostReactionRequest struct {
	PostId           uint64 `json:"postId"`
	PostReactionType `json:"reactionType"`
	AuthorId         uint `json:"authorId"`
}

type AddFileRequest struct {
	Url       string `json:"url"`
	Extension string `json:"extension"`
	EntityID  uint   `json:"entityID"`
}

type FileUploadResponse struct {
	Url       string `json:"url"`
	ID        string `json:"id"`
	Extension string `json:"extension"`
}
