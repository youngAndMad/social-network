package service

import (
	"context"
	"fmt"
	"github.com/minio/minio-go/v7"
	"mime/multipart"
	"storage-service/internal/entity"
	"time"
)

type MinioService struct {
	minio *minio.Client
}

func NewMinioService(minio *minio.Client) *MinioService {
	return &MinioService{
		minio: minio,
	}
}

func (s *MinioService) UploadFile(source entity.AttachmentSource, target int, file *multipart.FileHeader) (entity.File, error) {

	targetPath := fmt.Sprintf("%d", target)

	src, err := file.Open()
	if err != nil {
		return entity.File{}, err
	}
	defer src.Close()

	objectName := targetPath + "/" + file.Filename
	_, err = s.minio.PutObject(context.Background(), entity.GetBucket(source).Name, objectName, src, file.Size, minio.PutObjectOptions{
		ContentType: file.Header.Get("Content-Type"),
	})
	if err != nil {
		fmt.Println(err)
		return entity.File{}, err
	}

	fileDoc := entity.File{
		Bucket:       string(source),
		FileName:     file.Filename,
		Extension:    getFileExtension(file.Filename),
		UploadedTime: time.Now(),
		Url:          objectName,
		Source:       source,
		Target:       int64(target),
	}

	return fileDoc, nil
}

func (s *MinioService) RemoveFile(source entity.AttachmentSource, fileName string, target int64) error {
	targetPath := fmt.Sprintf("%d/%s", target, fileName)
	return s.minio.RemoveObject(context.Background(), entity.GetBucket(source).Name, targetPath, minio.RemoveObjectOptions{})
}
