package main

import (
	"github.com/gin-gonic/gin"
	"log"
)

func main() {

	r := gin.Default()
	r.POST("/upload", UploadFileHandler)
	if err := r.Run(":8080"); err != nil {
		log.Fatal(err)
	}
}
