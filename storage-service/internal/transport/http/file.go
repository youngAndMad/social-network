package http

import (
	"github.com/gin-gonic/gin"
	"github.com/minio/minio-go/v7"
	"go.mongodb.org/mongo-driver/mongo"
	"net/http"
	"storage-service/internal/entity"
	"storage-service/internal/service"
	"strconv"
)

type FileHandler struct {
	fileService  *service.FileService
	minioService *service.MinioService
}

func (h *FileHandler) UploadFile(c *gin.Context) {
	source := c.PostForm("source")
	target, err := strconv.Atoi(c.PostForm("target"))

	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	file, err := c.FormFile("file")
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "file is required"})
		return
	}

	fileEntity, err := h.minioService.UploadFile(entity.AttachmentSource(source), target, file)

	if err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	err = h.fileService.SaveFile(fileEntity)

	if err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}
}

func (h *FileHandler) RemoveFile(c *gin.Context) {
	fileID := c.Query("fileID")

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
	fileID := c.Query("fileID")

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

	routes := r.Group("/api/v1/file/")

	routes.DELETE(":id", h.RemoveFile)
	routes.GET(":id", h.GetFile)
	routes.POST("", h.UploadFile)
}
