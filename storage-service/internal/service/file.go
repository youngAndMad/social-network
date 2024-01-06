package service

import(
	"storage-service/internal/repository"
	"storage-service/internal/entity"
)

type FileService struct {
	repo * repository.File
}

func NewFileService (repo * repository.File) *FileService{
	return &FileService{
		repo: repo,
	}
}

func(fs *FileService) FindById(id string) (entity.File, error){
	return fs.repo.FindById(id)
}