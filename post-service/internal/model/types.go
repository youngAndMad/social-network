package model

type AddCommentRequest struct {
	OwnerID string `json:"ownerId"`
	Content string `json:"content"`
	PostID  uint64 `json:"postId"`
}

type AddPostRequest struct {
	Content string   `json:"content"`
	Type    PostType `json:"type"`
	OwnerID string   `json:"ownerId"`
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
	OwnerID          string `json:"ownerId"`
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
