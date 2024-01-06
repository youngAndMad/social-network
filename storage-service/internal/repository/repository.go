package repository

import (
	"storage-service/internal/entity"
	"go.mongodb.org/mongo-driver/mongo"
)

type Repository struct{
	File
}

type File interface{
	DeleteById(id string) (error)
	FindById(id string) (entity.File, error)
}

func NewMongoRepository(mongoClient * mongo.Client) *Repository{
	return &Repository{
		File: NewFileRepository(mongoClient), // fixme 
	}
}