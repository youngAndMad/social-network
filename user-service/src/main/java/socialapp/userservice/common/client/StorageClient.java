package socialapp.userservice.common.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface StorageClient {

    @PostExchange(contentType = "multipart/form-data")
    ResponseEntity<?> upload(@RequestParam(value = "source", required = false, defaultValue = "USER_PROFILE_IMAGE") String source,
                             @RequestParam Long target,
                             @RequestParam("file") MultipartFile file
    );
}
