package service

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"log"
	"storage-service/internal/entity"
)

type FileService struct {
	mongo      *mongo.Client
	collection *mongo.Collection
}

func NewFileService(mongo *mongo.Client, collection *mongo.Collection) *FileService {
	return &FileService{
		mongo:      mongo,
		collection: collection,
	}
}

func (s *FileService) GetFile(fileID string) (*entity.File, error) {
	objID, err := primitive.ObjectIDFromHex(fileID)
	if err != nil {
		fmt.Printf("Error converting fileID to ObjectID: %v\n", err)
		return nil, err
	}

	filter := bson.M{"_id": objID}

	var file entity.File
	err = s.collection.FindOne(context.Background(), filter).Decode(&file)
	if err != nil {
		fmt.Printf("Error retrieving file from MongoDB: %v\n", err)
		return nil, err
	}

	return &file, nil
}

func (s *FileService) RemoveFile(fileID string) (entity.File, error) {
	objID, err := primitive.ObjectIDFromHex(fileID)

	if err != nil {
		return entity.File{}, err
	}

	filter := bson.M{"_id": objID}
	var file entity.File

	err = s.collection.FindOneAndDelete(context.Background(), filter).Decode(&file)

	if err != nil {
		return entity.File{}, err
	}

	return file, nil
}

func (s *FileService) SaveFile(file entity.File) (interface{}, error) {
	fmt.Println(s.collection.Name())
	result, err := s.collection.InsertOne(context.Background(), file)
	if err != nil {
		log.Printf("Error inserting file: %v", err)
		return nil, err
	}

	id := result.InsertedID
	return id, nil
}
