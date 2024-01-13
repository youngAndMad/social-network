package entity

import "strings"

type AttachmentSource string

const (
	UserProfileImage   AttachmentSource = "USER_PROFILE_IMAGE"
	ChatMessageContent AttachmentSource = "CHAT_MESSAGE_CONTENT"
	ChatAvatarImage    AttachmentSource = "CHAT_AVATAR_IMAGE"
	ChannelAvatarImage AttachmentSource = "CHANNEL_AVATAR_IMAGE"
	PostContent        AttachmentSource = "POST_CONTENT"
)

var Buckets = []string{
	strings.ToLower(strings.Replace(string(UserProfileImage), "_", "-", -1)),
	strings.ToLower(strings.Replace(string(ChatMessageContent), "_", "-", -1)),
	strings.ToLower(strings.Replace(string(ChatAvatarImage), "_", "-", -1)),
	strings.ToLower(strings.Replace(string(ChannelAvatarImage), "_", "-", -1)),
	strings.ToLower(strings.Replace(string(PostContent), "_", "-", -1)),
}
