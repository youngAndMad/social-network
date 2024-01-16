package socialapp.newsservice.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import socialapp.newsservice.configuration.FeignClientConfiguration;

import java.util.List;


@FeignClient(name = "golang-service", url = "http://localhost:7070/api/v1/file", configuration = FeignClientConfiguration.class)
public interface StorageServiceClient {
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response uploadFiles(@RequestParam("source") String source,
                         @RequestParam("target") Long target,
                         @RequestParam("file") List<MultipartFile> multipartFiles);
}
