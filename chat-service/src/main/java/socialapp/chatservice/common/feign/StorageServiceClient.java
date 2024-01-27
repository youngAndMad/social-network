package socialapp.chatservice.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import socialapp.chatservice.model.dto.FileMetadata;

import java.util.List;

@FeignClient(name = "storage-service", path = "api/v1/storage-service/")
public interface StorageServiceClient {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileMetadata[]> uploadFiles(
            @RequestParam("source") String source,
            @RequestParam("target") Long target,
            @RequestPart("file") List<MultipartFile> multipartFile
    );

}
