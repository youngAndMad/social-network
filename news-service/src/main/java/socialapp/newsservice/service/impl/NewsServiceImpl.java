package socialapp.newsservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import socialapp.newsservice.client.StorageServiceClient;
import socialapp.newsservice.entity.News;
import socialapp.newsservice.repository.NewsRepository;
import socialapp.newsservice.service.NewsService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final StorageServiceClient storageServiceClient;
    private final ObjectMapper objectMapper;

    @Override
    public News saveNews(String title, String content, List<MultipartFile> multipartFiles) {
        News newNews = News.builder()
                .title(title)
                .content(content)
                .build();
        News savedNews = newsRepository.save(newNews);

        Response response = storageServiceClient.uploadFiles("NEWS_CONTENT", savedNews.getId(), multipartFiles);
        // implement the logic
        return null;
    }
}