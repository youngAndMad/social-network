package config

import (
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
	"time"
)

var (
	MongoURI             = "mongodb://mongouser:mongopassword@localhost:27018/storage.file?authSource=admin"
	MongoDBName          = "storage"
	MongoConnectTimeout  = 10 * time.Second
	MongoFilesCollection = "File"
)

func InitMongoClient() *mongo.Client {
	ctx, cancel := context.WithTimeout(context.Background(), MongoConnectTimeout)
	defer cancel()

	client, err := mongo.Connect(ctx, options.Client().ApplyURI(MongoURI))
	if err != nil {
		log.Fatal(err)
	}
	err = client.Ping(ctx, nil)
	if err != nil {
		log.Fatalf("Failed to ping MongoDB: %v", err)
	}

	return client
}
