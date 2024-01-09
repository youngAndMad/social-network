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

func (s *PostService) CreatePost(request *model.AddPostRequest) error {
	var post model.Post

	post.Content = request.Content
	post.AuthorID = request.AuthorID
	post.Type = request.Type

	return s.DB.Create(post).Error
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
	return s.DB.Save(&post).Error
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

	return s.DB.Delete(&post).Error
}
