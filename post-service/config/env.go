package config

import (
	"fmt"
	"github.com/spf13/viper"
)

type Env struct {
	Port  string
	DBUrl string
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
