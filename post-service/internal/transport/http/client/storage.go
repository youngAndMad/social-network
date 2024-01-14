package client

import (
	"bytes"
	"fmt"
	"io"
	"mime/multipart"
	"net/http"
	"os"
)

type StorageClient struct {
	client http.Client
}

func (c *StorageClient) UploadFile(source string, target int, file *os.File) error {
	var requestBody bytes.Buffer
	writer := multipart.NewWriter(&requestBody)
	fieldName := "file"

	part, err := writer.CreateFormFile(fieldName, file.Name())
	if err != nil {
		return fmt.Errorf("failed to create form file: %v", err)
	}

	_, err = io.Copy(part, file)
	if err != nil {
		return fmt.Errorf("failed to copy file content: %v", err)
	}

	writer.Close()

	request, err := http.NewRequest("POST", "http://localhost:7070/api/v1/file", &requestBody)
	if err != nil {
		return fmt.Errorf("failed to create HTTP request: %v", err)
	}

	request.Header.Set("Content-Type", writer.FormDataContentType())

	response, err := c.client.Do(request)
	if err != nil {
		return fmt.Errorf("failed to perform HTTP request: %v", err)
	}
	defer response.Body.Close()

	if response.StatusCode != http.StatusOK {
		return fmt.Errorf("unexpected status code: %d", response.StatusCode)
	}

	return nil
}
