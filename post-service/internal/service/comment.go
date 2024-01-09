package service

import (
	"gorm.io/gorm"
	"post-service/internal/model"
)

type CommentService struct {
	DB          *gorm.DB
	postService *PostService
}

func NewCommentService(DB *gorm.DB) *CommentService {
	return &CommentService{
		DB:          DB,
		postService: NewPostService(DB),
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

	result := s.DB.Create(comment)

	return result.Error
}

func (s *CommentService) DeleteComment(commentId uint64) (err error) {
	var comment model.Comment
	result := s.DB.First(&comment, commentId)

	if result.Error != nil {
		return result.Error
	}

	result = s.DB.Delete(&comment)
	return result.Error
}

func (s *CommentService) UpdateCommentContent(commentId uint64, content string) error {
	var comment model.Comment
	result := s.DB.First(&comment, commentId)
	if result.Error != nil {
		return result.Error
	}

	comment.Content = content
	result = s.DB.Save(&comment)
	return result.Error
}
