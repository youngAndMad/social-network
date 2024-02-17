package main

import (
	"github.com/gin-gonic/gin"
	"log"
	"post-service/config"
	"post-service/internal/transport/http"
)

func main() {
	env, err := config.LoadEnv(".")
	if err != nil {
		log.Fatal(err)
	}

	server := gin.Default()
	db := config.Init(env.DBUrl)

	http.RegisterPostRoutes(server, db)
	http.RegisterCommentRoutes(server, db)
	http.RegisterPostReactionRoutes(server, db)

	_err := server.Run(env.Port)

	if _err != nil {
		log.Fatal(err)
	}
}
