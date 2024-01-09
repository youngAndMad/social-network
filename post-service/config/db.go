package config

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

	migrate(db, model.Post{})
	migrate(db, model.Comment{})

	return db
}

func migrate(db *gorm.DB, dist interface{}) {
	err := db.AutoMigrate(dist)
	if err != nil {
		log.Fatalln(err)
		return
	}
}
