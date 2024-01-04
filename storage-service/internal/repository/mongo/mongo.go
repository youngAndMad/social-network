package mongo

import (
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"storage-service/config
)

var MongoClient *mongo.Client

func InitMongo() error {
	mongoConfig := config.NewMongoConfig()
	clientOptions := options.Client().ApplyURI(mongoConfig.Url)
	client, err := mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		return err
	}
	err = client.Ping(context.TODO(), nil)
	if err != nil {
		return err
	}
	MongoClient = client
	return nil
}
