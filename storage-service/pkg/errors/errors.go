package errors

import (
	"errors"
	"fmt"
	"net/http"
)

var (
	ErrFileNotFound = errors.New("file not found")
	ErrFileSizeLimit = errors.New("file size limit")
	ErrAbstract = errors.New("abstract application error")
)

func Fail(err error, place string) error {
	return fmt.Errorf("%s: %s", place, err.Error())
}

func WebFail(status int) error {
	return fmt.Errorf(http.StatusText(status))
}