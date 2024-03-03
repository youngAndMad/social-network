package http

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/minio/minio-go/v7"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"io"
	"net/http"
	"storage-service/internal/entity"
	"storage-service/internal/service"
)

type FileHandler struct {
	fileService  *service.FileService
	minioService *service.MinioService
}

func (h *FileHandler) UploadFiles(c *gin.Context) {
	source := c.Query("source")
	target := c.Query("target")
	form, err := c.MultipartForm()
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
	fmt.Println("hola")
	var response []entity.FileUploadResponse
	fmt.Println("test")
	for _, fileEntity := range fileEntities {
		id, err := h.fileService.SaveFile(fileEntity)
		if err != nil {
			bindError(c, http.StatusInternalServerError, err)
			return
		}
		if oid, ok := id.(primitive.ObjectID); ok {
			fileEntity.ID = oid
		}
		response = append(response, entity.ToFileUploadResponse(fileEntity))
	}

	c.JSON(http.StatusOK, response)
}

func (h *FileHandler) UploadFile(c *gin.Context) {
	source := c.Query("source")
	target := c.Query("target")

	file, err := c.FormFile("file")
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	fileEntity, err := h.minioService.UploadFile(entity.AttachmentSource(source), target, file)
	if err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	id, err := h.fileService.SaveFile(fileEntity)
	if err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}
	if oid, ok := id.(primitive.ObjectID); ok {
		fileEntity.ID = oid
	}

	response := entity.ToFileUploadResponse(fileEntity)

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

func (h *FileHandler) GetFiles(c *gin.Context) {

	fileIDs := c.QueryArray("id")

	if len(fileIDs) == 0 {
		c.JSON(http.StatusBadRequest, gin.H{"error": "at least one file ID is required"})
		return
	}

	var files []*entity.File
	for _, fileID := range fileIDs {
		file, err := h.fileService.GetFile(fileID)

		if err != nil {
			bindError(c, http.StatusNotFound, err)
		}

		files = append(files, file)
	}

	c.JSON(http.StatusOK, files)
}

func (h *FileHandler) RemoveFiles(c *gin.Context) {
	fileIDs := c.QueryArray("id")

	for _, fileID := range fileIDs {
		file, err := h.fileService.RemoveFile(fileID)
		if err != nil {
			bindError(c, http.StatusNotFound, err)
			return
		}
		err = h.minioService.RemoveFile(file.Source, file.FileName, file.Target)
		if err != nil {
			bindError(c, http.StatusInternalServerError, err)
			return
		}
	}
	c.Status(http.StatusNoContent)
}

func (h *FileHandler) DownloadFile(c *gin.Context) {
	// api/v1/file/{source}/{target}/{filename}

	source := entity.AttachmentSource(c.Param("source"))
	id := c.Param("target")
	filename := c.Param("filename")

	object, err := h.minioService.DownloadFile(source, id, filename)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "File not found"})
		return
	}
	defer object.Close()

	// Set appropriate headers
	c.Header("Content-Description", "File Transfer")
	c.Header("Content-Disposition", fmt.Sprintf("attachment; filename=%s", filename))
	c.Header("Content-Type", "application/octet-stream")
	c.Header("Content-Transfer-Encoding", "binary")
	c.Header("Expires", "0")
	c.Header("Cache-Control", "must-revalidate")
	c.Header("Pragma", "public")

	// Stream the file content to the response writer
	if _, err := io.Copy(c.Writer, object); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to download file"})
		return
	}
}

func NewFileRoutes(r *gin.Engine, collection *mongo.Collection, mongo *mongo.Client, minio *minio.Client) {

	fileService := service.NewFileService(mongo, collection)
	minioService := service.NewMinioService(minio)

	h := &FileHandler{
		fileService:  fileService,
		minioService: minioService,
	}

	routes := r.Group("/api/v1/file")

	routes.DELETE("/", h.RemoveFile)
	routes.GET("/", h.GetFile)
	routes.GET("/download/:source/:target/:filename", h.DownloadFile)
	routes.POST("", h.UploadFiles)
	routes.POST("/single", h.UploadFile)
	routes.GET("/files", h.GetFiles)
	routes.DELETE("/files", h.RemoveFiles)
}
