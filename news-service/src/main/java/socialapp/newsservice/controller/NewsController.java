package socialapp.newsservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import socialapp.newsservice.model.entity.News;
import socialapp.newsservice.service.NewsService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
//@CrossOrigin(origins = "http://127.0.0.1:4200", allowedHeaders = "*", methods = {
//        RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH
//},allowCredentials = "false")
@Slf4j
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    ResponseEntity<News> uploadNews(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam("files") List<MultipartFile> multipartFiles,
            @RequestParam Boolean emailSending
    ) {
        log.info(title + " " + content + " " + emailSending + " " + multipartFiles);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsService.saveNews(title, content, multipartFiles, emailSending));
    }

    @GetMapping
    ResponseEntity<Page<News>> getAllNews(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        Page<News> all = newsService.getAll(page, pageSize);
        return ResponseEntity.ok(all);
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
