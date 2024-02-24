package config

import (
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"log"
	"post-service/internal/model"
)

func Init(url string) *gorm.DB {
	db, err := gorm.Open(postgres.Open(url), &gorm.Config{})
	if err != nil {
		log.Fatalf("failed to connect to database: %v", err)
	}

	autoMigrateModels(db, &model.Post{}, &model.Comment{}, &model.PostReaction{}, &model.File{})

	return db
}

func autoMigrateModels(db *gorm.DB, models ...interface{}) {
	for _, md := range models {
		if err := db.AutoMigrate(md); err != nil {
			log.Fatalf("failed to auto migrate md %T: %v", md, err)
		}
	}
}
