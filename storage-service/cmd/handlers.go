package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"storage-service/internal/entity"
	"strconv"
)

func UploadFileHandler(c *gin.Context) {
	attachmentSource := c.PostForm("attachmentSource")
	entityID, err := strconv.Atoi(c.PostForm("entityID"))
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "invalid entityID"})
		return
	}

	if attachmentSource == "" {
		c.JSON(http.StatusBadRequest, gin.H{"error": "attachmentSource is required"})
		return
	}

	file, err := c.FormFile("file")
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "file is required"})
		return
	}

	fileService := entity.NewFileService()
	fileURL, err := fileService.UploadFile(attachmentSource, entityID, file)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "file uploaded successfully", "url": fileURL})
}
