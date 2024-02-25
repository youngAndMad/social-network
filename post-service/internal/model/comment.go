package model

import "gorm.io/gorm"

type Comment struct {
	gorm.Model
	Content string `json:"content"`
	PostID  uint   `json:"postId"`
	OwnerID string `json:"ownerId"`
	Post    Post
	Files   []File `gorm:"polymorphic:Owner;"`
}
