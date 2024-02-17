package model

import "gorm.io/gorm"

type File struct {
	gorm.Model
	Url       string `json:"url"`
	Extension string `json:"extension"`
	PostID    uint   `gorm:"not null" json:"postId"`
}
