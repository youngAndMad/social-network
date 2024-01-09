package main

import (
	// "post-service/config"
	"post-service/internal/db"
    "post-service/internal/transport/http"
	"github.com/gin-gonic/gin"
)

func main() {
	// env,err := config.LoadConfig()

	// if err != nil{
	// 	log.Fatal(err)
	// }

	port := ":1212"

	r := gin.Default()
    g := db.Init("postgres://postgres:postgres@localhost:5432/sn_post_service")

	r.GET("/", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"port": port,
		})
	})

    http.RegisterRoutes(r,g)
	r.Run(port)
}
