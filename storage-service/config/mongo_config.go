package config

import "os"

type MongoConfig struct{
	Url string
}

func NewMongoConfig() *MongoConfig{
	return &MongoConfig{
		Url: os.Getenv("MONGO_URL"),
	}
}