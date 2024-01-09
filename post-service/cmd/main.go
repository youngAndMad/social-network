package main

import (
	"github.com/gin-gonic/gin"
	"log"
	"post-service/config"
	"post-service/internal/transport/http"
)

func main() {

	port := ":1212"

	server := gin.Default()
	db := config.Init("postgres://postgres:postgres@localhost:5432/sn_post_service")

	http.RegisterPostRoutes(server, db)
	http.RegisterCommentRoutes(server, db)
	err := server.Run(port)

	if err != nil {
		log.Fatal(err)
	}
}
