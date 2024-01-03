package config

import "os"

type ServerConfig struct{
	Address string
}

func NewServerCgf() *ServerConfig {
	return &ServerConfig{
		Address: os.Getenv("SERVER_ADDR"),
	}
}