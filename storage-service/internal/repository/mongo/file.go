package mongo

import (
	"context"
	"fmt"
	"log"
	"storage-service/internal/entity"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

type FileRepository struct {
	mongoClient *mongo.Client
	collection  *mongo.Collection
}

func NewFileRepository(mongoClient *mongo.Client, collection *mongo.Collection) *FileRepository {
	return &FileRepository{
		mongoClient: mongoClient,
		collection:  collection,
	}
}

func (repo *FileRepository) FindById(id string) (entity.File, error) {
	objectID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		log.Fatal(err)
		return entity.File{}, err
	}

	filter := bson.M{"_id": objectID}

	var result entity.File
	err = repo.collection.FindOne(context.TODO(), filter).Decode(&result)
	if err != nil {
		log.Fatal(err)
		return entity.File{}, err
	}

	return result, nil
}

func (repo *FileRepository) DeleteById(id string) error {
	filter := bson.M{"_id": id}

	result, err := repo.collection.DeleteOne(context.TODO(), filter)

	if err != nil {
		log.Fatal(err)
		return err
	}

	fmt.Printf("Deleted %v documents\n", result.DeletedCount)

	return nil
}
