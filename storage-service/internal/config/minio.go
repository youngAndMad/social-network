package config

import (
	"github.com/minio/minio-go/v7"
	"github.com/minio/minio-go/v7/pkg/credentials"
	"log"
)

var (
	MinioEndpoint  = "localhost:9000"
	MinioAccessKey = "minioadmin"
	MinioSecretKey = "minioadmin"
	MinioBucket    = "storage"
)

func InitMinioClient() *minio.Client {
	minioClient, err := minio.New(MinioEndpoint, &minio.Options{
		Creds:  credentials.NewStaticV4(MinioAccessKey, MinioSecretKey, ""),
		Secure: false,
	})
	if err != nil {
		log.Fatal(err)
	}

	return minioClient
}
