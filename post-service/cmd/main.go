package main

import (
	"github.com/gin-gonic/gin"
	"log"
	"post-service/internal/db"
	"post-service/internal/transport/http"
)

func main() {

	port := ":1212"

	r := gin.Default()
	g := db.Init("postgres://postgres:postgres@localhost:5432/sn_post_service")

	http.RegisterPostRoutes(r, g)
	err := r.Run(port)

	if err != nil {
		log.Fatal(err)
	}
}
