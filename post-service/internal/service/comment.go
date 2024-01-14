package service

import (
	"gorm.io/gorm"
	"net/http"
	"post-service/internal/model"
	"post-service/internal/transport/http/client"
)

type CommentService struct {
	DB          *gorm.DB
	postService *PostService
}

func NewCommentService(DB *gorm.DB) *CommentService {
	return &CommentService{
		DB:          DB,
		postService: NewPostService(DB, client.NewStorageClient(http.Client{})),
	}
}

func (s *CommentService) AddComment(request model.AddCommentRequest) (err error) {

	post, err := s.postService.GetPostByID(request.PostID)
	if err != nil {
		return err
	}

	comment := &model.Comment{
		PostID:  post.ID,
		Content: request.Content,
	}

	return s.DB.Create(comment).Error
}

func (s *CommentService) DeleteComment(commentId uint64) (err error) {
	var comment model.Comment
	result := s.DB.First(&comment, commentId)

	if result.Error != nil {
		return result.Error
	}

	return s.DB.Delete(&comment).Error
}

func (s *CommentService) UpdateCommentContent(commentId uint64, content string) error {
	var comment model.Comment
	result := s.DB.First(&comment, commentId)
	if result.Error != nil {
		return result.Error
	}

	comment.Content = content
	return s.DB.Save(&comment).Error
}
