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
	post.OwnerID = request.OwnerID
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
			OwnerID:   post.ID,
			OwnerType: "post",
		}
		fmt.Println(fileRecord)
		if err := s.DB.Create(&fileRecord).Error; err != nil {
			return err
		}
	}
	return nil
}
func (s *PostService) GetOwnerPosts(ownerId string, postType model.PostType) ([]model.Post, error) {
	var posts []model.Post
	fmt.Printf("Getting posts for owner: %s and type: %v\n", ownerId, postType)
	result := s.DB.Where("owner_id = ? AND type = ?", ownerId, postType).Find(&posts)
	if result.Error != nil {
		fmt.Printf("Error: %v\n", result.Error)
	}
	fmt.Println(posts)
	return posts, result.Error
}

func (s *PostService) UpdatePostContent(id string, content string) (*model.Post, error) {
	var post model.Post
	if err := s.DB.Model(&post).Where("id = ?", id).Update("content", content).Error; err != nil {
		return nil, err
	}
	return &post, nil
}

func (s *PostService) GetPostByID(postID uint64) (*model.Post, error) {
	var post model.Post
	result := s.DB.Preload("Comments").Preload("Reactions").Preload("Files").First(&post, postID)
	return &post, result.Error
}

func (s *PostService) DeletePost(postID uint64) error {
	fmt.Printf("Deleting post with ID: %d\n", postID)
	result := s.DB.Delete(&model.Post{}, postID)
	if result.Error != nil {
		fmt.Printf("Error deleting post: %v\n", result.Error)
		return result.Error
	}
	return nil
}
