package config

import (
	"github.com/spf13/viper"
	"time"
)

type Env struct {
	MongoURI             string        `mapstructure:"MONGO_URI"`
	MongoDBName          string        `mapstructure:"MONGO_DB_NAME"`
	MongoConnectTimeout  time.Duration `mapstructure:"MONGO_CONNECTION_TIMEOUT"`
	MongoFilesCollection string        `mapstructure:"MONGO_FILES_CONNECTION"`
	MinioEndpoint        string        `mapstructure:"MINIO_ENDPOINT"`
	MinioSecretKey       string        `mapstructure:"MINIO_SECRET_KEY"`
	MinioAccessKey       string        `mapstructure:"MINIO_ACCESS_KEY"`
}

func LoadEnv(path string) (env Env, err error) {
	viper.AddConfigPath(path)
	viper.SetConfigName("app")
	viper.SetConfigType("env")
	viper.AutomaticEnv()

	err = viper.ReadInConfig()
	if err != nil {
		return
	}

	err = viper.Unmarshal(&env)
	return
}
