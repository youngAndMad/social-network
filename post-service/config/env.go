package config

import (
	"github.com/spf13/viper"
	"log"
)

type Env struct {
	Port  string `mapstructure:"PORT"`
	DBUrl string `mapstructure:"DB_URL"`
}

func LoadConfig() (env Env, err error) {
	viper.AddConfigPath("./.env")
	// viper.SetConfigName("dev")
	// viper.SetConfigType("env")

	viper.AutomaticEnv()

	err = viper.ReadInConfig()
	if err != nil {
		log.Fatal(err)
		return
	}

	err = viper.Unmarshal(&env)
	if err != nil {
		log.Fatal(err)
		return
	}

	return env, nil
}
