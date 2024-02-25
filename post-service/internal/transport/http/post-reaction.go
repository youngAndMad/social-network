package http

import (
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
	"net/http"
	"post-service/internal/model"
	"post-service/internal/service"
	"strconv"
)

type PostReactionHandler struct {
	postReactionService *service.PostReactionService
}

func (h *PostReactionHandler) AddPostReaction(c *gin.Context) {
	body := model.AddPostReactionRequest{}

	if err := c.BindJSON(&body); err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if err := h.postReactionService.AddPostReaction(body); err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	c.Status(http.StatusCreated)
}

func (h *PostReactionHandler) DeletePostReaction(c *gin.Context) {
	id, err := strconv.ParseUint(c.Param("id"), 10, 64)
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if err := h.postReactionService.DeletePostReaction(id); err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}
	c.Status(http.StatusNoContent)
}

func RegisterPostReactionRoutes(r *gin.Engine, db *gorm.DB) {
	postService := service.NewPostReactionService(db)
	h := &PostReactionHandler{
		postReactionService: postService,
	}

	routes := r.Group("/api/v1/post-reaction")

	routes.POST("", h.AddPostReaction)
	routes.DELETE("/:id", h.DeletePostReaction)

}
