package service

import (
	"gorm.io/gorm"
	"post-service/internal/model"
	"post-service/internal/transport/http/client"
)

type PostReactionService struct {
	DB          *gorm.DB
	postService *PostService
}

func NewPostReactionService(DB *gorm.DB) *PostReactionService {
	return &PostReactionService{
		DB:          DB,
		postService: NewPostService(DB, client.NewStorageClient(DB)),
	}
}

func (s *PostReactionService) AddPostReaction(request model.AddPostReactionRequest) error {
	post, err := s.postService.GetPostByID(request.PostId)
	if err != nil {
		return err
	}

	postReaction := &model.PostReaction{
		PostId:       post.ID,
		OwnerID:      request.OwnerID,
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
