package config

import (
	"fmt"
	"github.com/spf13/viper"
)

type Env struct {
	Port              string `mapstructure:"PORT"`
	DBUrl             string `mapstructure:"DB_URL"`
	StorageServiceUrl string `mapstructure:"STORAGE_SERVICE_URL"`
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
