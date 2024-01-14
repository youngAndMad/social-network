package model

import "gorm.io/gorm"

type Comment struct {
	gorm.Model
	Content string `json:"content"`
	PostID  uint   `json:"postId"`
	//Files   []File `json:"files"`
}
