package entity

import (
	"context"
	"fmt"
	"github.com/minio/minio-go/v7"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
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
	targetPath := fmt.Sprintf("%s/%d", attachmentSource, entityID)

	src, err := file.Open()
	if err != nil {
		return "", err
	}

	defer func(src multipart.File) {
		err := src.Close()
		if err != nil {
		}
	}(src)

	bucketName := config.MinioBucket
	objectName := targetPath + "/" + file.Filename

	_, err = s.MinioClient.PutObject(context.Background(), bucketName, objectName, src, file.Size, minio.PutObjectOptions{
		ContentType: file.Header.Get("Content-Type"),
	})
	if err != nil {
		return "", err
	}

	fileDoc := File{
		Bucket:       bucketName,
		FileName:     file.Filename,
		Extension:    getFileExtension(file.Filename),
		UploadedTime: time.Now(),
		Url:          objectName,
		Source:       AttachmentSource(attachmentSource),
		Target:       int64(entityID),
	}

	collection := s.MongoClient.Database(config.MongoDBName).Collection(config.MongoFilesCollection)
	_, err = collection.InsertOne(context.Background(), fileDoc)
	if err != nil {
		return "", err
	}

	return objectName, nil
}

func getFileExtension(filename string) string {
	dotIndex := len(filename) - 1
	for i := len(filename) - 1; i >= 0; i-- {
		if filename[i] == '.' {
			dotIndex = i
			break
		}
	}
	return filename[dotIndex+1:]
}

func (s *FileService) GetFile(fileID string) (*File, error) {
	objID, err := primitive.ObjectIDFromHex(fileID)
	if err != nil {
		fmt.Printf("Error converting fileID to ObjectID: %v\n", err)
		return nil, err
	}

	filter := bson.M{"_id": objID}

	var file File
	collection := s.MongoClient.Database(config.MongoDBName).Collection(config.MongoFilesCollection)
	err = collection.FindOne(context.Background(), filter).Decode(&file)
	if err != nil {
		fmt.Printf("Error retrieving file from MongoDB: %v\n", err)
		return nil, err
	}

	return &file, nil
}

func (s *FileService) DeleteFile(fileID string) error {
	objID, err := primitive.ObjectIDFromHex(fileID)
	if err != nil {
		fmt.Printf("ObjectIDFromHex error: %v\n", err)
		return err
	}

	filter := bson.M{"_id": objID}
	fmt.Printf(fileID + "\n after filter")

	var file File
	collection := s.MongoClient.Database(config.MongoDBName).Collection(config.MongoFilesCollection)
	fmt.Printf("Before FindOneAndDelete")
	err = collection.FindOneAndDelete(context.Background(), filter).Decode(&file)
	fmt.Printf("After findOneAndDelete")
	if err != nil {
		return err
	}

	bucketName := file.Bucket
	objectName := file.Url

	err = s.MinioClient.RemoveObject(context.Background(), bucketName, objectName, minio.RemoveObjectOptions{})
	if err != nil {
		return err
	}

	return nil
}
