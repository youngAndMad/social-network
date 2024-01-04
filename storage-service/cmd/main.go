package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"log"
	"storage-service/internal/repository/mongo"
)

func main() {
	err := mongo.InitMongo()
	if err != nil {
		fmt.Println("Error initializing MongoDB:", err)
		return
	}
	r := gin.Default()
	// r.POST("/upload")
	//r.GET("/download/:filename")
	//r.POST("/delete/:filename")
	if err := r.Run(":8080"); err != nil {
		log.Fatal(err)
	}
}
