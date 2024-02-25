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
	authorId := c.PostForm("ownerID")

	postType := model.PostType(c.PostForm("type"))

	body := model.AddPostRequest{
		Content: content,
		OwnerID: authorId,
		Type:    postType,
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

func (h *PostHandler) GetOwnerPosts(c *gin.Context) {
	log.Print("GetOwnerPosts called")

	id := c.Query("ownerID")

	if posts, err := h.PostService.GetOwnerPosts(id, model.PostType(c.Query("postType"))); err != nil {
		bindError(c, http.StatusNotFound, err)
		return
	} else {
		c.JSON(http.StatusOK, &posts)
	}

}

func (h *PostHandler) UpdatePost(c *gin.Context) {
	body := model.UpdatePostRequest{}

	id := c.Param("id")

	if err := c.BindJSON(&body); err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	post, err := h.PostService.UpdatePostContent(id, body.Content)
	if err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	c.JSON(http.StatusOK, post)
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

	routes := r.Group("/api/v1/post")
	routes.GET("", h.GetOwnerPosts)
	routes.GET("/:id", h.GetPostById)
	routes.POST("", h.AddPost)
	routes.PUT("/:id", h.UpdatePost)
	routes.DELETE("/:id", h.DeletePost)
}
