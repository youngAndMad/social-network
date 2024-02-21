package socialapp.channelservice.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import socialapp.channelservice.model.payload.FileUploadResponse;

import java.util.List;

@FeignClient(name = "storage-service-client")
public interface StorageServiceClient {

    @PostMapping(value = "single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("source") String source,
            @RequestParam("target") String target,
            @RequestPart("file") MultipartFile multipartFile
    );
}
