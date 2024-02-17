package model

import "gorm.io/gorm"

type Comment struct {
	gorm.Model
	Content  string `json:"content"`
	PostID   uint   `json:"postId"`
	AuthorID uint64 `json:"authorId"`
	Files    []File `json:"files" gorm:"foreignKey:CommentID"`
}
