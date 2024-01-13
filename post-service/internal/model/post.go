package model

import "gorm.io/gorm"

type PostType string

const (
	UserProfilePost PostType = "USER_PROFILE_POST"
	ChannelPost     PostType = "CHANNEL_POST"
)

type Post struct {
	gorm.Model                // adds ID, created_at, etc.
	Content    string         `json:"content"`
	Type       PostType       `json:"type"`
	AuthorID   uint64         `json:"authorId"`
	Comments   []Comment      `json:"comments"`
	Reactions  []PostReaction `json:"reactions"`
	Files      []File         `json:"files"`
}
