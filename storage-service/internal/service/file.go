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

const (
	FileCollection = "_file"
)

type FileService struct {
	mongo      *mongo.Client
	collection *mongo.Collection
}

func NewFileService(mongo *mongo.Client, database string) *FileService {
	return &FileService{
		mongo:      mongo,
		collection: mongo.Database(database).Collection(FileCollection),
	}
}

func (s *FileService) FindById(id string) (entity.File, error) {
	objectID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		log.Fatal(err)
		return entity.File{}, err
	}

	filter := bson.M{"_id": objectID}

	var result entity.File
	err = s.collection.FindOne(context.TODO(), filter).Decode(&result)
	if err != nil {
		log.Fatal(err)
		return entity.File{}, err
	}

	return result, nil
}

func (s *FileService) DeleteById(id string) error {
	filter := bson.M{"_id": id}

	result, err := s.collection.DeleteOne(context.TODO(), filter)

	if err != nil {
		log.Fatal(err)
		return err
	}

	fmt.Printf("Deleted %v documents\n", result.DeletedCount)

	return nil
}
