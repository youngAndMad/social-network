package http

import (
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
	"log"
	"net/http"
	"post-service/internal/model"
	"post-service/internal/service"
	"post-service/internal/transport/http/client"
	"strconv"
)

type PostHandler struct {
	PostService *service.PostService
}

func (h *PostHandler) AddPost(c *gin.Context) {
	content := c.PostForm("content")
	authorId, err := strconv.ParseUint(c.PostForm("authorId"), 10, 64)

	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	postType := model.PostType(c.PostForm("type"))

	body := model.AddPostRequest{
		Content:  content,
		AuthorID: authorId,
		Type:     postType,
	}

	form, err := c.MultipartForm()
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	files := form.File["file"]

	if err := h.PostService.CreatePost(&body, files); err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	c.Status(http.StatusCreated)
}

func (h *PostHandler) GetAuthorPost(c *gin.Context) {
	log.Print("GetAuthorPosts called")

	id, err := strconv.ParseUint(c.Param("id"), 10, 64)
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if posts, err := h.PostService.GetAuthorPosts(id, model.PostType(c.Param("postType"))); err != nil {
		bindError(c, http.StatusNotFound, err)
		return
	} else {
		c.JSON(http.StatusOK, &posts)
	}

}

func (h *PostHandler) UpdatePost(c *gin.Context) {
	body := model.UpdatePostRequest{}

	id, err := strconv.ParseUint(c.Param("id"), 10, 64)
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	var post model.Post

	if err := c.BindJSON(&body); err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if err := h.PostService.UpdatePostContent(id, body.Content); err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	c.JSON(http.StatusCreated, &post)
}

func (h *PostHandler) GetPostById(c *gin.Context) {
	id, err := strconv.ParseUint(c.Param("id"), 10, 64)
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if post, err := h.PostService.GetPostByID(id); err != nil {
		bindError(c, http.StatusNotFound, err)
		return
	} else {
		c.JSON(http.StatusOK, &post)
	}
}

func (h *PostHandler) DeletePost(c *gin.Context) {
	id, err := strconv.ParseUint(c.Param("id"), 10, 64)
	if err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if err := h.PostService.DeletePost(id); err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	c.Status(http.StatusNoContent)
}

func RegisterPostRoutes(r *gin.Engine, db *gorm.DB) {
	storageClient := client.NewStorageClient(db)
	postService := service.NewPostService(db, storageClient)
	h := &PostHandler{
		PostService: postService,
	}

	routes := r.Group("/post")
	routes.GET("", h.GetAuthorPost)
	routes.GET("/:id", h.GetPostById)
	routes.POST("", h.AddPost)
	routes.PUT("/:id", h.UpdatePost)
	routes.DELETE("/:id", h.DeletePost)
}
