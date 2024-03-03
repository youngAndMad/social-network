package config

import (
	"fmt"
	"github.com/spf13/viper"
	"time"
)

type Env struct {
	Port                 string        `mapstructure:"PORT"`
	MongoURI             string        `mapstructure:"MONGO_URI"`
	MongoDBName          string        `mapstructure:"MONGO_DB_NAME"`
	MongoConnectTimeout  time.Duration `mapstructure:"MONGO_CONNECTION_TIMEOUT"`
	MongoFilesCollection string        `mapstructure:"MONGO_FILES_COLLECTION"`
	MinioEndpoint        string        `mapstructure:"MINIO_ENDPOINT"`
	MinioSecretKey       string        `mapstructure:"MINIO_SECRET_KEY"`
	MinioAccessKey       string        `mapstructure:"MINIO_ACCESS_KEY"`
	MinioToken           string        `mapstructure:"MINIO_TOKEN"`
	MinioSecure          bool          `mapstructure:"MINIO_SECURE"`
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
	fmt.Print(env)
	return
}
