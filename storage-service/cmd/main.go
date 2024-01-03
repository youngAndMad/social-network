package main

import (
	"context"
	"github.com/gin-gonic/gin"
	"github.com/minio/minio-go/v7"
	"github.com/minio/minio-go/v7/pkg/credentials"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
	"net/http"
	"path/filepath"
	"time"
)

const (
	minioEndpoint   = ""
	minioAccessKey  = ""
	minioSecretKey  = ""
	minioBucket     = ""
	mongoURI        = ""
	mongoDB         = ""
	mongoCollection = ""
)

var minioClient *minio.Client
var mongoClient *mongo.Client

type File struct {
	ID           string    `bson:"_id,omitempty" ,json:"id"`
	Bucket       string    `bson:"bucket" ,json:"bucket"`
	FileName     string    `bson:"file_name" ,json:"file_name"`
	Extension    string    `bson:"extension" ,json:"extension"`
	UploadedTime time.Time `bson:"uploaded_time" ,json:"uploaded_time"`
	Url          string    `bson:"url" ,json:"url"`
}

func initMinio() {
	var err error
	minioClient, err = minio.New(minioEndpoint, &minio.Options{
		Creds:  credentials.NewStaticV4(minioAccessKey, minioSecretKey, ""),
		Secure: false,
	})
	if err != nil {
		log.Fatal(err)
	}
}
func initMongo() {
	clientOptions := options.Client().ApplyURI(mongoURI)
	client, err := mongo.Connect(context.Background(), clientOptions)
	if err != nil {
		log.Fatal(err)
	}

	err = client.Ping(context.Background(), nil)
	if err != nil {
		log.Fatal(err)
	}

	mongoClient = client
}

func main() {
	initMongo()
	initMinio()

	r := gin.Default()

	r.POST("/upload", uploadFile)
	//r.GET("/download/:filename")
	//r.POST("/delete/:filename")
	if err := r.Run(":8080"); err != nil {
		log.Fatal(err)
	}
}

func uploadFile(c *gin.Context) {
	user := c.PostForm("user")
	filename := c.PostForm("filename")

	file, err := c.FormFile("file")
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Missing file in the request"})
		return
	}

	src, err := file.Open()
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to open the file"})
		return
	}
	defer src.Close()

	objectName := filepath.Join(user, filename)
	contentType := file.Header.Get("Content-Type")

	_, err = minioClient.PutObject(
		c.Request.Context(),
		minioBucket,
		objectName,
		src,
		file.Size,
		minio.PutObjectOptions{ContentType: contentType},
	)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to upload the file to Minio"})
		return
	}

	// Добавляем метаданные в MongoDB
	fileMetadata := File{
		Bucket:       user,
		FileName:     filename,
		Extension:    filepath.Ext(filename),
		UploadedTime: time.Now(),
		Url:          objectName,
	}

	collection := mongoClient.Database(mongoDB).Collection(mongoCollection)
	result, err := collection.InsertOne(context.Background(), fileMetadata)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to store file metadata in MongoDB"})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "File uploaded successfully", "fileID": result.InsertedID})
}
