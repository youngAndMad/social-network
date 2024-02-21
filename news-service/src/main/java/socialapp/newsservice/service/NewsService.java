package socialapp.newsservice.service;


import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import socialapp.newsservice.model.entity.News;

import java.util.List;

public interface NewsService {
    News saveNews(String title, String content, List<MultipartFile> multipartFiles, Boolean emailSending);

    Page<News> getAll(int page, int pageSize);

    News getById(Long id);

    void deleteNewsById(Long id);

    News updateNews(Long id, String title, String content);
}
