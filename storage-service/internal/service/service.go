package service

import(
	"storage-service/internal/entity"
)

type Service struct{
	File
}

type File interface{
	DeleteById(id string) (error)
	FindById(id string) (entity.File, error)
}

