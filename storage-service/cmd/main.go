package main

import (
	"github.com/gin-gonic/gin"
	"log"
)

func main() {
	r := gin.Default()
	r.GET("/get", GetFileHandler)
	r.POST("/upload", UploadFileHandler)
	r.DELETE("/delete", DeleteFileHandler)
	if err := r.Run(":8080"); err != nil {
		log.Fatal(err)
	}
}
