package socialapp.newsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import socialapp.newsservice.entity.News;
import socialapp.newsservice.service.NewsService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    ResponseEntity<News> uploadNews(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam("files") List<MultipartFile> multipartFiles,
            @RequestParam Boolean emailSending
    ) {
        return ResponseEntity.ok(newsService.saveNews(title, content, multipartFiles, emailSending));
    }

    @GetMapping
    ResponseEntity<Page<News>> getAllNews(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(newsService.getAll(page, pageSize));
    }

    @GetMapping("{id}")
    ResponseEntity<News> getNews(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getById(id));
    }

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNewsById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    ResponseEntity<News> updateNews(@RequestBody News news) {
        return ResponseEntity.ok(newsService.updateNews(news));
    }
}
