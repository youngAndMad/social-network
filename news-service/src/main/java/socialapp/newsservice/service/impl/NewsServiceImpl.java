package socialapp.newsservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import socialapp.newsservice.common.client.StorageServiceClient;
import socialapp.newsservice.model.entity.FileMetaData;
import socialapp.newsservice.model.entity.News;
import socialapp.newsservice.common.exception.EntityNotFoundException;
import socialapp.newsservice.repository.FileMetaDataRepository;
import socialapp.newsservice.repository.NewsRepository;
import socialapp.newsservice.service.NewsService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {
    private static final String NEWS_CONTENT = "NEWS_CONTENT";
    private final NewsRepository newsRepository;
    private final StorageServiceClient storageServiceClient;
    private final FileMetaDataRepository fileMetaDataRepository;

    @Override
    public News saveNews(String title, String content, List<MultipartFile> multipartFiles, Boolean emailSending) {
        var newNews = News.builder()
                .title(title)
                .content(content)
                .emailSending(emailSending)
                .emailListInitialized(false)
                .publishDate(LocalDateTime.now())
                .build();
        var savedNews = newsRepository.save(newNews);
        var fileUploadResponse = storageServiceClient.uploadFiles(NEWS_CONTENT, savedNews.getId(), multipartFiles);
        var files = Arrays.stream(Objects.requireNonNull(fileUploadResponse.getBody()))
                .map(it -> new FileMetaData(it.url(), it.id(), savedNews, it.extension()))
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
                .orElseThrow(() -> new EntityNotFoundException(News.class, id));
    }

    @Override
    public void deleteNewsById(Long id) {
        var news = getById(id);
        storageServiceClient.removeFiles(
                news.getFiles().stream().map(FileMetaData::getFileId).toList()
        );
        newsRepository.deleteById(id);
    }

    @Override
    public News updateNews(Long id,String title,String content) {
        var news = getById(id);
        news.setTitle(title);
        news.setContent(content);
        return newsRepository.save(news);
    }
}