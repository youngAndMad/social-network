package socialapp.userservice.common.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import socialapp.userservice.model.dto.FileUploadResponseDto;

@FeignClient(name = "storage-client")
public interface StorageClient {

    Logger log = LoggerFactory.getLogger(StorageClient.class);

    @PostMapping("single")
    @CircuitBreaker(name = "storageService", fallbackMethod = "circuitBreakerCallback")
    @Retry(name ="storageService")
    ResponseEntity<FileUploadResponseDto> upload(@RequestParam(value = "source", required = false, defaultValue = "USER_PROFILE_IMAGE") String source,
                                                 @RequestParam Long target,
                                                 @RequestParam("file") MultipartFile file
    );

    default  ResponseEntity<FileUploadResponseDto> circuitBreakerCallback(Throwable throwable){
        log.error("circuitBreakerCallback err = {}", throwable.getMessage());
        return ResponseEntity.ok(null);
    }


    default  ResponseEntity<FileUploadResponseDto> retryCallback(Throwable throwable){
        log.error("retryCallback err = {}", throwable.getMessage());
        return ResponseEntity.ok(null);
    }
}
