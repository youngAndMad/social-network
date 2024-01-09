package config

import "github.com/spf13/viper"

type Env struct {
    Port  string `mapstructure:"PORT"`
    DBUrl string `mapstructure:"DB_URL"`
}

func LoadConfig() (c Env, err error) {
    viper.AddConfigPath("")
    viper.SetConfigName("dev")
    viper.SetConfigType("env")

    viper.AutomaticEnv()

    err = viper.ReadInConfig()

    if err != nil {
        return
    }

    err = viper.Unmarshal(&c)

    return
}