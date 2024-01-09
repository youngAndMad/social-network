package service

import (
	"gorm.io/gorm"
	"post-service/internal/model"
)

type PostReactionService struct {
	DB          *gorm.DB
	postService *PostService
}

func NewPostReactionService(DB *gorm.DB) *PostReactionService {
	return &PostReactionService{
		DB:          DB,
		postService: NewPostService(DB),
	}
}

func (s *PostReactionService) AddPostReaction(request model.AddPostReactionRequest) error {
	post, err := s.postService.GetPostByID(request.PostId)
	if err != nil {
		return err
	}

	postReaction := &model.PostReaction{
		PostId:       post.ID,
		AuthorId:     request.AuthorId,
		ReactionType: request.PostReactionType,
	}

	result := s.DB.Create(&postReaction)

	return result.Error
}

func (s *PostReactionService) DeletePostReaction(id uint64) error {
	var postReaction model.PostReaction

	if result := s.DB.First(&postReaction, id); result.Error != nil {
		return result.Error
	}

	return s.DB.Delete(&postReaction, id).Error
}
