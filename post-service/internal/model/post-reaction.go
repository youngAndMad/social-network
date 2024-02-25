package model

import (
	"gorm.io/gorm"
)

type PostReactionType string

const (
	Like    PostReactionType = "LIKE"
	Dislike PostReactionType = "DISLIKE"
	Share   PostReactionType = "SHARE"
)

type PostReaction struct {
	gorm.Model
	ReactionType PostReactionType `json:"reactionType"`
	OwnerID      string           `json:"ownerId"`
	PostId       uint             `json:"postId"`
}
