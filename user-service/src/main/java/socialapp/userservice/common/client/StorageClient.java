package socialapp.userservice.common.client;

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

    @PostMapping("single")
    ResponseEntity<FileUploadResponseDto> upload(@RequestParam(value = "source", required = false, defaultValue = "USER_PROFILE_IMAGE") String source,
                                                 @RequestParam Long target,
                                                 @RequestParam("file") MultipartFile file
    );
}
