package service

import (
	"fmt"
	"gorm.io/gorm"
	"mime/multipart"
	"post-service/internal/model"
	"post-service/internal/transport/http/client"
)

type PostService struct {
	DB            *gorm.DB
	storageClient *client.StorageClient
}

func NewPostService(db *gorm.DB, storageClient *client.StorageClient) *PostService {
	return &PostService{
		DB:            db,
		storageClient: storageClient,
	}
}

func (s *PostService) CreatePost(request *model.AddPostRequest, files []*multipart.FileHeader) error {
	var post model.Post

	post.Content = request.Content
	post.AuthorID = request.AuthorID
	post.Type = request.Type

	if err := s.DB.Create(&post).Error; err != nil {
		return err
	}

	for _, file := range files {
		err, fileUploadResponse := s.storageClient.UploadFile("POST_CONTENT", int(post.ID), file)
		if err != nil {
			return err
		}
		fileRecord := &model.File{
			Url:       fileUploadResponse.Url,
			Extension: fileUploadResponse.Extension,
			PostID:    post.ID,
		}
		fmt.Println(fileRecord)
		//if err := s.DB.Create(&fileRecord).Error; err != nil {
		//	return err
		//}
	}
	return nil
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
	result := s.DB.Preload("Comments").Preload("Reactions").Preload("Files").First(&post, postID)
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
