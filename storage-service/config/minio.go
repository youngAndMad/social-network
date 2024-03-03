package config

import (
	"github.com/minio/minio-go/v7"
	"github.com/minio/minio-go/v7/pkg/credentials"
	"log"
)

func InitMinioClient(env Env) *minio.Client {
	minioClient, err := minio.New(env.MinioEndpoint, &minio.Options{
		Creds:  credentials.NewStaticV4(env.MinioAccessKey, env.MinioSecretKey, env.MinioToken),
		Secure: env.MinioSecure,
	})
	if err != nil {
		log.Println("Error")
		log.Fatal(err)
	}
	return minioClient
}
