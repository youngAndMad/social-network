package main

import (
	"context"
	"github.com/gin-gonic/gin"
	"github.com/minio/minio-go/v7"
	"log"
	"storage-service/config"
	"storage-service/internal/entity"
	"storage-service/internal/transport/http"
)

func main() {
	env, err := config.LoadEnv(".")

	if err != nil {
		log.Fatal(err)
		return
	}

	server := gin.Default()

	minioClient := config.InitMinioClient(env)
	mongoClient := config.InitMongoClient(env)
	fileCollection := mongoClient.Database(env.MongoDBName).Collection(env.MongoFilesCollection)

	http.NewFileRoutes(server, fileCollection, mongoClient, minioClient)
	checkMinioBuckets(minioClient)

	err = server.Run(env.Port)

	if err != nil {
		log.Fatal(err)
	}

	log.Printf("app running on %s", env.Port)
}

func checkMinioBuckets(m *minio.Client) {
	for _, bucket := range entity.Buckets {

		exists, err := m.BucketExists(context.Background(), bucket)
		if err != nil {
			log.Fatal(err)
		}

		if !exists {
			err = m.MakeBucket(context.Background(), bucket, minio.MakeBucketOptions{})
			if err != nil {
				log.Fatal(err)
			}
			log.Printf("Bucket '%s' created successfully.", bucket)
		} else {
			log.Printf("Bucket '%s' already exists.", bucket)
		}
	}
}
