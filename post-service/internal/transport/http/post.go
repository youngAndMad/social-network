package http

import (
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
	"log"
	"net/http"
	"post-service/internal/model"
	"post-service/internal/service"
	"strconv"
)

type PostHandler struct {
	PostService *service.PostService
}

func (h *PostHandler) AddPost(c *gin.Context) {
	body := model.AddPostRequest{}

	if err := c.BindJSON(&body); err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	var post model.Post

	post.Content = body.Content
	post.AuthorID = body.AuthorID
	post.Type = body.Type

	if err := h.PostService.CreatePost(&post); err != nil {
		bindError(c, http.StatusInternalServerError, err)
		return
	}

	c.JSON(http.StatusCreated, &post)
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

func bindError(c *gin.Context, status int, err error) {
	_err := c.AbortWithError(status, err)

	if _err != nil {
		log.Fatal(err)
	}
}

func RegisterPostRoutes(r *gin.Engine, db *gorm.DB) {
	postService := service.NewPostService(db)
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
