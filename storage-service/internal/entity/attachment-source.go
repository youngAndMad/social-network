package entity

import (
	"strings"
)

type AttachmentSource string

const (
	UserProfileImage   AttachmentSource = "USER_PROFILE_IMAGE"
	ChatMessageContent AttachmentSource = "CHAT_MESSAGE_CONTENT"
	ChatAvatarImage    AttachmentSource = "CHAT_AVATAR_IMAGE"
	ChannelAvatarImage AttachmentSource = "CHANNEL_AVATAR_IMAGE"
	PostContent        AttachmentSource = "POST_CONTENT"
)

type Bucket struct {
	Source AttachmentSource
	Name   string
}

var Buckets = []Bucket{
	{UserProfileImage, strings.ToLower(strings.Replace(string(UserProfileImage), "_", "-", -1))},
	{ChatMessageContent, strings.ToLower(strings.Replace(string(ChatMessageContent), "_", "-", -1))},
	{ChatAvatarImage, strings.ToLower(strings.Replace(string(ChatAvatarImage), "_", "-", -1))},
	{ChannelAvatarImage, strings.ToLower(strings.Replace(string(ChannelAvatarImage), "_", "-", -1))},
	{PostContent, strings.ToLower(strings.Replace(string(PostContent), "_", "-", -1))},
}

func GetBucket(source AttachmentSource) Bucket {
	for _, bucket := range Buckets {
		if bucket.Source == source {
			return bucket
		}
	}
	return Bucket{}
}
