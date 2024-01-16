package socialapp.newsservice.service;


import org.springframework.web.multipart.MultipartFile;
import socialapp.newsservice.entity.News;

import java.util.List;

public interface NewsService {
    News saveNews(String title, String content, List<MultipartFile> multipartFiles);
}
