package service

import (
	"bytes"
	"context"
	"fmt"
	"github.com/minio/minio-go/v7"
	"io"
	"mime/multipart"
	"os"
	"storage-service/internal/entity"
	"strconv"
)

type MinioService struct {
	minio *minio.Client
}

func NewMinioService(client *minio.Client) *MinioService {
	return &MinioService{
		minio: client,
	}
}

func (s *MinioService) CreateBucket(bucketName string) {
	err := s.minio.MakeBucket(context.Background(), bucketName, minio.MakeBucketOptions{})
	if err != nil {
		panic(err)
	}
}

func (s *MinioService) DeleteObject(bucketName string, filePath string) error {
	return s.minio.RemoveObject(context.Background(), bucketName, filePath, minio.RemoveObjectOptions{ForceDelete: true})
}

func (s *MinioService) ReadObject(bucketName string, filePath string) ([]byte, error) {
	contentBuffer, err := s.minio.GetObject(context.Background(), bucketName, filePath, minio.GetObjectOptions{})
	if err != nil {
		return nil, err
	}

	contentBytes := new(bytes.Buffer)
	if _, err := io.Copy(contentBytes, contentBuffer); err != nil {
		return nil, err
	}
	return contentBytes.Bytes(), nil
}

func (s *MinioService) UploadObject(source entity.AttachmentSource, targetId uint64, file *multipart.File) (string, error) {

	fileStat, err := file()
	if err != nil {
		return "", err
	}

	uploadInfo, err := s.minio.PutObject(
		context.Background(),
		string(source)+"/"+strconv.FormatUint(targetId, 10),
		file.Name(),
		file,
		fileStat.Size(),
		minio.PutObjectOptions{},
	)

	if err != nil {
		return "", err
	}
	fmt.Printf("Uploaded Info : %v", uploadInfo)

	return uploadInfo.Key, nil
}
