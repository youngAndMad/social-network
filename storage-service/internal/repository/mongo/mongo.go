package mongo

import (


	"github.com/youngAndMad/social-network/storage-service/config"
	
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var mongoClient *mongo.Client

func initMongo(mongoConfig * MongoConfig)