package client

import (
	"bytes"
	"encoding/json"
	"errors"
	"fmt"
	"github.com/go-resty/resty/v2"
	"gorm.io/gorm"
	"io"
	"mime/multipart"
	"post-service/config"
	"post-service/internal/model"
)

type StorageClient struct {
	client *resty.Client
	DB     *gorm.DB
}

func NewStorageClient(db *gorm.DB) *StorageClient {
	return &StorageClient{
		client: resty.New(),
		DB:     db,
	}
}

func (c *StorageClient) UploadFile(source string, target int, fileHeader *multipart.FileHeader) (error, *model.FileUploadResponse) {
	var buf bytes.Buffer
	form := multipart.NewWriter(&buf)
	env, err := config.LoadEnv(".")
	fileWriter, err := form.CreateFormFile("file", fileHeader.Filename)
	if err != nil {
		return err, nil
	}
	file, err := fileHeader.Open()
	if err != nil {
		return err, nil
	}
	defer file.Close()
	_, err = io.Copy(fileWriter, file)
	if err != nil {
		return err, nil
	}
	err = form.Close()
	if err != nil {
		return err, nil
	}
	resp, err := c.client.R().
		SetQueryString(fmt.Sprintf("source=%s&target=%d", source, target)).
		SetBody(buf.Bytes()).
		SetHeader("Content-Type", form.FormDataContentType()).
		Post(env.StorageServiceUrl)
	if err != nil {
		return err, nil
	}
	if resp.IsError() {
		return fmt.Errorf("upload failed: %v", resp.Status()), nil
	}
	var fileUploadResponses []model.FileUploadResponse
	if err := json.Unmarshal(resp.Body(), &fileUploadResponses); err != nil {
		fmt.Println("Error decoding JSON:", err)
		return err, nil
	}
	if len(fileUploadResponses) == 0 {
		return errors.New("empty response received"), nil
	}
	fileUploadResponse := fileUploadResponses[0]

	return nil, &fileUploadResponse
}
