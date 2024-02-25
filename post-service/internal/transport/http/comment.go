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
	content := c.PostForm("content")
	authorId := c.PostForm("ownerID")
	postId, err := strconv.ParseUint(c.PostForm("postId"), 10, 64)
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	body := model.AddCommentRequest{
		OwnerID: authorId,
		Content: content,
		PostID:  postId,
	}
	form, err := c.MultipartForm()
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}
	files := form.File["file"]
	if err := h.commentService.AddComment(&body, files); err != nil {
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

	routes := r.Group("/api/v1/comment")
	routes.POST("", h.AddComment)
	routes.PUT("/:id", h.UpdateComment)
	routes.DELETE("/:id", h.DeleteComment)
}
