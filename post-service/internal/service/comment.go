package service

import (
	"fmt"
	"gorm.io/gorm"
	"mime/multipart"
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
		postService: NewPostService(DB, client.NewStorageClient(DB)),
	}
}

func (s *CommentService) AddComment(request *model.AddCommentRequest, files []*multipart.FileHeader) (err error) {

	post, err := s.postService.GetPostByID(request.PostID)
	if err != nil {
		return err
	}

	comment := &model.Comment{
		OwnerID: request.OwnerID,
		PostID:  post.ID,
		Content: request.Content,
	}
	if err := s.DB.Create(&comment).Error; err != nil {
		return err
	}

	for _, file := range files {
		err, fileUploadResponse := s.postService.storageClient.UploadFile("COMMENT_CONTENT", int(comment.ID), file)
		if err != nil {
			return err
		}
		fileRecord := &model.File{
			Url:       fileUploadResponse.Url,
			Extension: fileUploadResponse.Extension,
			OwnerID:   comment.ID,
			OwnerType: "comment",
		}
		fmt.Println(fileRecord)
		if err := s.DB.Create(&fileRecord).Error; err != nil {
			return err
		}
	}
	return nil
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
