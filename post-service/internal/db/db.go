package db

import (
    "log"

	"post-service/internal/model"
    "gorm.io/driver/postgres"
    "gorm.io/gorm"
)

func Init(url string) *gorm.DB {
    db, err := gorm.Open(postgres.Open(url), &gorm.Config{})

    if err != nil {
        log.Fatalln(err)
    }

    db.AutoMigrate(&model.Post{})
    db.AutoMigrate(&model.Comment{})

    return db
}