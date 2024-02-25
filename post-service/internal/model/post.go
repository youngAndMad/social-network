package model

import "gorm.io/gorm"

type PostType string

const (
	UserProfilePost PostType = "USER_PROFILE_POST"
	ChannelPost     PostType = "CHANNEL_POST"
)

type Post struct {
	gorm.Model
	Content   string         `json:"content"`
	Type      PostType       `json:"type"`
	OwnerID   string         `json:"ownerId"`
	Comments  []Comment      `json:"comments" gorm:"constraint:OnDelete:CASCADE"`
	Reactions []PostReaction `json:"reactions" gorm:"constraint:OnDelete:CASCADE"`
	Files     []File         `gorm:"polymorphic:Owner;constraint:OnDelete:CASCADE"`
}
