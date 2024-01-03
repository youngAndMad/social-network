package config

import (
	"os"
	"strconv"
)

type MinioConfig struct {
	Endpoint  string
	AccessKey string
	SecretKey string
	Token     string
	Secure    bool
}

func NewMinioConfig() *MinioConfig {
	secureStr := os.Getenv("MINIO_SECURE")

	secure, err := strconv.ParseBool(secureStr)
	
	if err != nil {
		secure = false
	}

	return &MinioConfig{
		Endpoint:  os.Getenv("MINIO_ENDPOINT"),
		AccessKey: os.Getenv("MINIO_ACCESS_KEY"),
		SecretKey: os.Getenv("MINIO_SECRET_KEY"),
		Token:     os.Getenv("MINIO_TOKEN"),
		Secure:    secure,
	}
}
