package model

import "gorm.io/gorm"

type Comment struct {
	gorm.Model
	Content  string `json:"content"`
	PostID   uint   `json:"postId"`
	AuthorId uint   `json:"authorId"`
	//Files   []File `json:"files"`
}
