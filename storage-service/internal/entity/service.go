package entity

import (
	"context"
	"fmt"
	"github.com/minio/minio-go/v7"
	"go.mongodb.org/mongo-driver/mongo"
	"log"
	"mime/multipart"
	"storage-service/internal/config"
	"time"
)

type FileService struct {
	MinioClient *minio.Client
	MongoClient *mongo.Client
}

func NewFileService() *FileService {
	return &FileService{
		MinioClient: config.InitMinioClient(),
		MongoClient: config.InitMongoClient(),
	}
}

func (s *FileService) UploadFile(attachmentSource string, entityID int, file *multipart.FileHeader) (string, error) {
	err := s.ensureBucketExists(config.MinioBucket)
	log.Println(config.MinioBucket + " minioBucket")
	if err != nil {
		return "", err
	}

	sourceBucket := fmt.Sprintf("%s/%s", config.MinioBucket, attachmentSource)

	err = s.ensureBucketExists(sourceBucket)
	log.Println(sourceBucket + " sourceBucket\n")
	fmt.Println(err)
	if err != nil {
		return "", err
	}

	entityBucket := fmt.Sprintf("%s/%d", sourceBucket, entityID)
	log.Println(entityBucket + " entityBucket")
	err = s.ensureBucketExists(entityBucket)
	if err != nil {
		return "", err
	}

	fileName := file.Filename
	filePath := fmt.Sprintf("%s/%s", entityBucket, fileName)
	fileURL, err := s.uploadToMinio(filePath, file, fileName)
	if err != nil {
		return "", err
	}

	err = s.saveFileMetadata(attachmentSource, entityID, fileName, fileURL)
	if err != nil {
		return "", err
	}

	return fileURL, nil
}

func (s *FileService) ensureBucketExists(bucketName string) error {
	exists, err := s.MinioClient.BucketExists(context.Background(), bucketName)
	if err != nil {
		return err
	}
	if !exists {
		err := s.MinioClient.MakeBucket(context.Background(), bucketName, minio.MakeBucketOptions{})
		if err != nil {
			return err
		}
	}
	return nil
}

func (s *FileService) uploadToMinio(filePath string, file *multipart.FileHeader, fileName string) (string, error) {
	src, err := file.Open()
	if err != nil {
		return "", err
	}
	defer src.Close()

	// Загрузить файл в Minio.
	_, err = s.MinioClient.FPutObject(
		context.Background(),
		config.MinioBucket,
		fileName,
		filePath,
		minio.PutObjectOptions{
			ContentType: file.Header.Get("Content-Type"),
		},
	)
	if err != nil {
		return "", err
	}

	// Сформировать URL для загруженного файла.
	fileURL := s.MinioClient.EndpointURL().String() + "/" + config.MinioBucket + "/" + filePath
	return fileURL, nil
}

func (s *FileService) saveFileMetadata(attachmentSource string, entityID int, fileName string, fileURL string) error {
	filesCollection := s.MongoClient.Database(config.MongoDBName).Collection(config.MongoFilesCollection)

	file := File{
		Bucket:       attachmentSource,
		FileName:     fileName,
		Extension:    "",
		UploadedTime: time.Now(),
		Url:          fileURL,
		Source:       AttachmentSource(attachmentSource),
		Target:       int64(entityID),
	}

	// Вставить метаданные файла в MongoDB.
	_, err := filesCollection.InsertOne(context.TODO(), file)
	if err != nil {
		return err
	}

	return nil
}
