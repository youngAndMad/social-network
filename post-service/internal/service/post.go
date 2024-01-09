package service

import (
	"gorm.io/gorm"
	"post-service/internal/model"
)

type PostService struct {
	DB *gorm.DB
}

func NewPostService(db *gorm.DB) *PostService {
	return &PostService{
		DB: db,
	}
}

func (s *PostService) CreatePost(post *model.Post) error {
	result := s.DB.Create(post)
	return result.Error
}

func (s *PostService) GetAuthorPosts(authorID uint64, postType model.PostType) ([]model.Post, error) {
	var posts []model.Post
	result := s.DB.Find(&posts, "author_id = ? AND type = ?", authorID, postType)
	return posts, result.Error
}

func (s *PostService) UpdatePostContent(postID uint64, content string) error {
	var post model.Post
	result := s.DB.First(&post, postID)
	if result.Error != nil {
		return result.Error
	}

	post.Content = content
	result = s.DB.Save(&post)
	return result.Error
}

func (s *PostService) GetPostByID(postID uint64) (*model.Post, error) {
	var post model.Post
	result := s.DB.First(&post, postID)
	return &post, result.Error
}

func (s *PostService) DeletePost(postID uint64) error {
	var post model.Post
	result := s.DB.First(&post, postID)
	if result.Error != nil {
		return result.Error
	}

	result = s.DB.Delete(&post)
	return result.Error
}
