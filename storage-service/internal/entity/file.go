package entity

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
	"time"
)

type File struct {
	ID           primitive.ObjectID `json:"id,omitempty" bson:"_id,omitempty"`
	Bucket       string             `bson:"bucket" json:"bucket"`
	FileName     string             `bson:"file_name" json:"fileName"`
	Extension    string             `bson:"extension" json:"extension"`
	UploadedTime time.Time          `bson:"uploaded_time" json:"uploadedTime"`
	Url          string             `bson:"url" json:"url"`
	Source       AttachmentSource   `bson:"attachment_source" json:"attachmentSource"`
	Target       string             `bson:"target" json:"target"`
}

type FileUploadResponse struct {
	Url       string             `json:"url"`
	Extension string             `json:"extension"`
	ID        primitive.ObjectID `json:"id"`
}

func ToFileUploadResponse(file File) FileUploadResponse {
	return FileUploadResponse{
		Url:       file.Url,
		ID:        file.ID,
		Extension: file.Extension,
	}
}
