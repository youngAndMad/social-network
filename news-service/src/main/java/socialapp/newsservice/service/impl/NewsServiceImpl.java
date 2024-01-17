package socialapp.newsservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import socialapp.newsservice.client.StorageServiceClient;
import socialapp.newsservice.entity.FileMetaData;
import socialapp.newsservice.entity.News;
import socialapp.newsservice.payload.exception.EntityNotFoundException;
import socialapp.newsservice.repository.FileMetaDataRepository;
import socialapp.newsservice.repository.NewsRepository;
import socialapp.newsservice.service.NewsService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private static final String NEWS_CONTENT = "NEWS_CONTENT";
    private final NewsRepository newsRepository;
    private final StorageServiceClient storageServiceClient;
    private final FileMetaDataRepository fileMetaDataRepository;

    @Override
    public News saveNews(String title, String content, List<MultipartFile> multipartFiles, Boolean emailSending) {
        News newNews = News.builder()
                .title(title)
                .content(content)
                .emailSending(emailSending)
                .build();
        News savedNews = newsRepository.save(newNews);
        var fileUploadResponse = storageServiceClient.uploadFiles(NEWS_CONTENT, savedNews.getId(), multipartFiles);

        var files = Arrays.stream(fileUploadResponse.getBody())
                .map(f -> new FileMetaData(f.url(),f.id(),savedNews))
                .map(fileMetaDataRepository::save)
                .collect(Collectors.toSet());

        savedNews.setFiles(files);
        newsRepository.saveAndFlush(savedNews);
        return savedNews;
    }

    @Override
    public Page<News> getAll(
            int page, int pageSize
    ) {
        return newsRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public News getById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News entity with id: " + id + " not found"));
    }

    @Override
    public void deleteNewsById(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new EntityNotFoundException("News with ID " + id + " not found");
        }
        newsRepository.deleteById(id);
    }

    @Override
    public News updateNews(News news) {
        return newsRepository.save(news);
    }
}