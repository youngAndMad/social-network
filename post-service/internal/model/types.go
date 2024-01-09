package model

type AddCommentRequest struct {
	Content string `json:"content"`
	PostID  uint64 `json:"postId"`
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
