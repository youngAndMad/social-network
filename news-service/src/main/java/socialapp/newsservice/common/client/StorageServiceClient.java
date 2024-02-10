package socialapp.newsservice.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import socialapp.newsservice.model.payload.File;
import socialapp.newsservice.model.payload.FileUploadResponse;

import java.util.List;

@FeignClient(name = "storage-service-client")
public interface StorageServiceClient {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileUploadResponse[]> uploadFiles(
            @RequestParam("source") String source,
            @RequestParam("target") Long target,
            @RequestPart("file") List<MultipartFile> multipartFile
    );

    @DeleteMapping("/files")
    ResponseEntity<HttpStatus> removeFiles(@RequestParam List<String> id);
}