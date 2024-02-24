package http

import (
	"github.com/gin-gonic/gin"
	"log"
)

func bindError(c *gin.Context, status int, err error) {
	_err := c.AbortWithError(status, err)
	if _err != nil {
		log.Fatal(err)
	}
}
