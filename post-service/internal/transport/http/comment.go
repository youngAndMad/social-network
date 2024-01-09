package http

import (
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
	"net/http"
	"post-service/internal/model"
	"post-service/internal/service"
	"strconv"
)

type CommentHandler struct {
	commentService *service.CommentService
}

func (h *CommentHandler) AddComment(c *gin.Context) {
	body := model.AddCommentRequest{}

	if err := c.BindJSON(&body); err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if err := h.commentService.AddComment(body); err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	c.Status(http.StatusCreated)
}

func (h *CommentHandler) UpdateComment(c *gin.Context) {
	body := model.UpdateCommentRequest{}
	commentID := c.Param("id")

	id, err := strconv.ParseUint(commentID, 10, 64)
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if err := c.BindJSON(&body); err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if err := h.commentService.UpdateCommentContent(id, body.Content); err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	c.Status(http.StatusNoContent)
}

func (h *CommentHandler) DeleteComment(c *gin.Context) {
	commentID := c.Param("id")

	id, err := strconv.ParseUint(commentID, 10, 64)
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if err := h.commentService.DeleteComment(id); err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	c.Status(http.StatusNoContent)
}

func RegisterCommentRoutes(r *gin.Engine, db *gorm.DB) {
	commentService := service.NewCommentService(db)
	h := &CommentHandler{
		commentService: commentService,
	}

	routes := r.Group("/comment")
	routes.POST("", h.AddComment)
	routes.PUT("/:id", h.UpdateComment)
	routes.DELETE("/:id", h.DeleteComment)
}
