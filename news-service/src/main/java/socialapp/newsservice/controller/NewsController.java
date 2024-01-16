package socialapp.newsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import socialapp.newsservice.entity.News;
import socialapp.newsservice.service.NewsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewsService newsService;
    @PostMapping
    ResponseEntity<News> uploadNews(@RequestParam String title, @RequestParam String content, @RequestParam("files") List<MultipartFile> multipartFiles){
        return ResponseEntity.ok(newsService.saveNews(title, content, multipartFiles));
    }
}
