package http

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/minio/minio-go/v7"
	"go.mongodb.org/mongo-driver/mongo"
	"log"
	"net/http"
	"storage-service/internal/entity"
	"storage-service/internal/service"
	"strconv"
)

type FileHandler struct {
	fileService  *service.FileService
	minioService *service.MinioService
}

func (h *FileHandler) UploadFiles(c *gin.Context) {
	fmt.Println(c.Request)
	fmt.Println(c.Request)
	source := c.Query("source")
	fmt.Println(source)
	target, err := strconv.Atoi(c.Query("target"))
	if err != nil {
		log.Printf("Hello world %d and %s", target, source)
		bindError(c, http.StatusBadRequest, err)
		return
	}

	form, err := c.MultipartForm()
	log.Println("i am here")
	log.Println(form)
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	files := form.File["file"]

	if len(files) == 0 {
		c.JSON(http.StatusBadRequest, gin.H{"error": "at least one file is required"})
		return
	}

	var fileEntities []entity.File

	for _, file := range files {
		fileEntity, err := h.minioService.UploadFile(entity.AttachmentSource(source), target, file)
		if err != nil {
			bindError(c, http.StatusInternalServerError, err)
			return
		}

		fileEntities = append(fileEntities, fileEntity)
	}

	var response []entity.FileUploadResponse

	for _, fileEntity := range fileEntities {
		err := h.fileService.SaveFile(fileEntity)
		if err != nil {
			bindError(c, http.StatusInternalServerError, err)
			return
		}
		response = append(response, entity.ToFileUploadResponse(fileEntity))
	}

	c.JSON(http.StatusOK, response)
}

func (h *FileHandler) RemoveFile(c *gin.Context) {
	fileID := c.Query("id")

	file, err := h.fileService.RemoveFile(fileID)

	if err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	err = h.minioService.RemoveFile(file.Source, file.FileName, file.Target)

	if err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	c.Status(http.StatusNoContent)
}

func (h *FileHandler) GetFile(c *gin.Context) {
	fileID := c.Query("id")

	file, err := h.fileService.GetFile(fileID)

	if err != nil {
		bindError(c, http.StatusNotFound, err)
		return
	}

	c.JSON(http.StatusOK, file)
}

func NewFileRoutes(r *gin.Engine, collection *mongo.Collection, mongo *mongo.Client, minio *minio.Client) {

	fileService := service.NewFileService(mongo, collection)
	minioService := service.NewMinioService(minio)

	h := &FileHandler{
		fileService:  fileService,
		minioService: minioService,
	}

	routes := r.Group("/api/v1/file")

	routes.DELETE("/:id", h.RemoveFile)
	routes.GET("/:id", h.GetFile)
	routes.POST("", h.UploadFiles)
}
