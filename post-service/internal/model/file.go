package model

import "gorm.io/gorm"

type File struct {
	gorm.Model
	Url       string `json:"url"`
	Extension string `json:"extension"`
	OwnerID   uint
	OwnerType string
}
