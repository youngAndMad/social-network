package client

import (
	"bytes"
	"fmt"
	"io"
	"io/ioutil"
	"mime/multipart"
	"net/http"
	"strconv"
)

type StorageClient struct {
	client http.Client
}

func NewStorageClient(client http.Client) *StorageClient {
	return &StorageClient{
		client: client,
	}
}

func (c *StorageClient) UploadFile(source string, target int, fileHeader *multipart.FileHeader) error {
	form := multipart.NewWriter(bytes.NewBuffer(nil))

	fileWriter, err := form.CreateFormFile("file", fileHeader.Filename)
	if err != nil {
		panic(err)
	}

	// Open the file using the file header
	file, err := fileHeader.Open()
	if err != nil {
		panic(err)
	}
	defer file.Close()

	// Copy the file contents to the form field
	_, err = io.Copy(fileWriter, file)
	if err != nil {
		panic(err)
	}

	err = form.WriteField("target", strconv.Itoa(target))
	if err != nil {
		return err
	}
	err = form.WriteField("source", source)
	if err != nil {
		return err
	}

	err = form.Close()

	if err != nil {
		return err
	}

	req, err := http.NewRequest("POST", "http://your-second-app-address/upload", form.FormData()) // Replace with the actual URL
	if err != nil {
		return err
	}
	req.Header.Set("Content-Type", form.FormDataContentType())

	resp, err := c.client.Do(req)
	if err != nil {
		return err
	}
	defer resp.Body.Close()

	// Check the response status
	if resp.StatusCode != http.StatusOK {
		// Handle non-200 status codes
		body, _ := ioutil.ReadAll(resp.Body)
		return fmt.Errorf("upload failed with status %d: %s", resp.StatusCode, body)
	}

	return nil // Upload successful
}
