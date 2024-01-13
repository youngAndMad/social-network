package entity

import (
	"time"
)

type File struct {
	ID           string           `bson:"_id,omitempty" json:"id"`
	Bucket       string           `bson:"bucket" json:"bucket"`
	FileName     string           `bson:"file_name" json:"fileName"`
	Extension    string           `bson:"extension" json:"extension"`
	UploadedTime time.Time        `bson:"uploaded_time" json:"uploadedTime"`
	Url          string           `bson:"url" json:"url"`
	Source       AttachmentSource `bson:"attachment_source" json:"attachmentSource"`
	Target       int64            `bson:"target" json:"target"`
}
