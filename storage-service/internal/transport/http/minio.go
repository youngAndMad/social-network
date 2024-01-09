package http

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"storage-service/internal/service"
)

type MinioHandler struct {
	minioService *service.MinioService
}

func (h MinioHandler) Upload(c *gin.Context) {
	err := c.Request.ParseMultipartForm(10 << 20) // 10 MB limit
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	files := c.Request.MultipartForm.File["files"]
	source := c.Request.MultipartForm.Value["attachmentSource"]
	target := c.Request.MultipartForm.Value["targetId"]

	for _,fileHeader := range files {
		file ,_:= fileHeader.Open()
		h.minioService.UploadObject(source, target,file)
	}
}
