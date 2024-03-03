package socialapp.newsservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import socialapp.newsservice.common.client.StorageServiceClient;
import socialapp.newsservice.model.entity.News;
import socialapp.newsservice.repository.FileMetaDataRepository;
import socialapp.newsservice.repository.NewsRepository;
import socialapp.newsservice.service.impl.NewsServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private StorageServiceClient storageServiceClient;

    @Mock
    private FileMetaDataRepository fileMetaDataRepository;
    @InjectMocks
    private NewsServiceImpl newsService;


    @Test
    void getAll() {
        List<News> newsList = new ArrayList<>();
        Page<News> newsPage = new PageImpl<>(newsList);
        when(newsRepository.findAll(any(Pageable.class))).thenReturn(newsPage);

        Page<News> result = newsService.getAll(0, 10);
        assertEquals(newsPage, result);
    }

    @Test
    void getById() {
        News news = new News();
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(news));

        News result = newsService.getById(1L);
        assertEquals(news, result);
    }

    @Test
    void updateNews() {
        News news = new News();
        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(news));
        when(newsRepository.save(any(News.class))).thenReturn(news);

        News result = newsService.updateNews(1L, "title", "content");
        assertEquals(news, result);
    }
}