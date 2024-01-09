package http

import (
	"log"
	"net/http"

	"post-service/internal/model"

	"github.com/gin-gonic/gin"

	"gorm.io/gorm"
)

type handler struct {
	DB *gorm.DB
}

type AddPostRequest struct {
	Content  string         `json:"content"`
	Type     model.PostType `json:"type"`
	AuthorID uint64         `json:"authorId"`
}

type UpdatePostRequest struct {
	Content string `json:"content"`
}

func (h handler) AddPost(c *gin.Context) {
	body := AddPostRequest{}

	if err := c.BindJSON(&body); err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	var post model.Post

	post.Content = body.Content
	post.AuthorID = body.AuthorID
	post.Type = body.Type

	if result := h.DB.Create(&post); result.Error != nil {
		bindError(c, http.StatusInternalServerError, result.Error)
		return
	}

	c.JSON(http.StatusCreated, &post)
}

func (h handler) GetAuthorPost(c *gin.Context) {
	id := c.Query("authorId")
	postType := c.Query("postType")

	log.Print("GetAuthorPosts called")

	var posts []model.Post

	if result := h.DB.Find(&posts, id, postType); result.Error != nil {
		bindError(c, http.StatusNotFound, result.Error)
		return
	}

	c.JSON(http.StatusOK, &posts)
}

func (h handler) UpdatePost(c *gin.Context) {
	body := UpdatePostRequest{}
	id := c.Param("authorId")

	var post model.Post

	if err := c.BindJSON(&body); err != nil {
		bindError(c, http.StatusBadRequest, err)
		return
	}

	if result := h.DB.First(&post, id); result.Error != nil {
		bindError(c, http.StatusInternalServerError, result.Error)
		return
	}

	post.Content = body.Content

	if result := h.DB.Model(&post).Update("content", post.Content); result.Error != nil {
		bindError(c, http.StatusInternalServerError, result.Error)
		return
	}
	c.JSON(http.StatusCreated, &post)
}

func (h handler) GetPostById(c *gin.Context) {
	id := c.Param("id")

	var post model.Post

	if result := h.DB.First(&post, id); result.Error != nil {
		bindError(c, http.StatusNotFound, result.Error)
		return
	}
	c.JSON(http.StatusOK, &post)
}

func (h handler) DeletePost(c *gin.Context) {
	id := c.Param("id")

	var post model.Post

	if result := h.DB.First(&post, id); result.Error != nil {
		bindError(c, http.StatusNotFound, result.Error)
		return
	}

	if result := h.DB.Delete(&post); result.Error != nil {
		bindError(c, http.StatusInternalServerError, result.Error)
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
	h := &handler{
		DB: db,
	}

	routes := r.Group("/post")
	routes.GET("", h.GetAuthorPost)
	routes.GET("/:id", h.GetPostById)
	routes.POST("", h.AddPost)
	routes.PUT("/:id", h.UpdatePost)
	routes.DELETE("/:id", h.DeletePost)
}
