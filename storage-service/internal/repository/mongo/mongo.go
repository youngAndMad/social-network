package mongo

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"storage-service/config"
)

const (
	Database = "test"
	FileCollection = "_file"
)

func InitMongo(mongoConfig *config.MongoConfig) (*mongo.Client, error) {
	fmt.Println("mongo initialization Url = ", mongoConfig.Url)
	clientOptions := options.Client().ApplyURI(mongoConfig.Url)
	mongoClient, err := mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		return nil, err
	}

	err = mongoClient.Ping(context.TODO(), nil)

	if err != nil {
		return nil, err
	}

	return mongoClient, nil
}
