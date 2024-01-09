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

func (h handler) AddPost(c *gin.Context) {
	body := AddPostRequest{}

	if err := c.BindJSON(&body); err != nil {
		c.AbortWithError(http.StatusBadRequest, err)
		return
	}

	var post model.Post

	post.Content = body.Content
	post.AuthorID = body.AuthorID
	post.Type = body.Type

	if result := h.DB.Create(&post); result.Error != nil {
		c.AbortWithError(http.StatusNotFound, result.Error)
		return
	}

	c.JSON(http.StatusCreated, &post)
}

func (h handler) GetAuthorPosts(c *gin.Context) {
	id := c.Param("authorId")
	postType := c.Param("postType")

	log.Print("GetAuthorPosts called")

	var posts []model.Post

	if result := h.DB.Find(&posts, id, postType); result.Error != nil {
		c.AbortWithError(http.StatusNotFound, result.Error)
		return
	}

	c.JSON(http.StatusOK, &posts)
}

func RegisterRoutes(r *gin.Engine, db *gorm.DB) {
	h := &handler{
		DB: db,
	}

	routes := r.Group("/post")
	routes.GET("/:authorId/:postType", h.GetAuthorPosts)
	routes.POST("", h.AddPost)
}
