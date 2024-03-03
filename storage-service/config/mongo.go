package config

import (
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
)

func InitMongoClient(env Env) *mongo.Client {
	ctx, cancel := context.WithTimeout(context.Background(), env.MongoConnectTimeout)
	defer cancel()

	client, err := mongo.Connect(ctx, options.Client().ApplyURI(env.MongoURI))
	if err != nil {
		log.Fatal(err)
	}
	err = client.Ping(ctx, nil)
	if err != nil {
		log.Fatalf("Failed to ping MongoDB: %v", err)
	}
	return client
}
