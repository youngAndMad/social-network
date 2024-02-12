package client

import (
	"bytes"
	"fmt"
	"github.com/go-resty/resty/v2"
	"io"
	"mime/multipart"
)

type StorageClient struct {
	client *resty.Client
}

func NewStorageClient() *StorageClient {
	return &StorageClient{
		client: resty.New(),
	}
}

func (c *StorageClient) UploadFile(source string, target int, fileHeader *multipart.FileHeader) error {
	var buf bytes.Buffer
	form := multipart.NewWriter(&buf)

	fileWriter, err := form.CreateFormFile("file", fileHeader.Filename)
	if err != nil {
		return err
	}

	file, err := fileHeader.Open()
	if err != nil {
		return err
	}
	defer file.Close()

	_, err = io.Copy(fileWriter, file)
	if err != nil {
		return err
	}

	err = form.Close()
	if err != nil {
		return err
	}

	resp, err := c.client.R().
		SetQueryString(fmt.Sprintf("source=%s&target=%d", source, target)).
		SetBody(buf.Bytes()).
		SetHeader("Content-Type", form.FormDataContentType()).
		Post("http://localhost:7070/api/v1/file")

	if err != nil {
		return err
	}

	if resp.IsError() {
		return fmt.Errorf("upload failed: %v", resp.Status())
	}

	return nil
}
