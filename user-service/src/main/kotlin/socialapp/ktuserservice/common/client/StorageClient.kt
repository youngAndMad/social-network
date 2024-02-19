package socialapp.ktuserservice.common.client

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import socialapp.ktuserservice.model.dto.FileUploadResponseDto

@FeignClient(name = "storage-service")
interface StorageClient {

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @PostMapping(value = ["single"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
//    @CircuitBreaker(name = "storageService", fallbackMethod = "circuitBreakerCallback")
//    @Retry(name = "storageService")
    fun upload(
        @RequestParam(value = "source") source: String="USER_PROFILE_IMAGE",
        @RequestParam target: Long,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<FileUploadResponseDto>

    @DeleteMapping
    fun deleteAvatar(@RequestParam target: Long,@RequestParam source: String = "USER_PROFILE_IMAGE")

    fun circuitBreakerCallback(throwable: Throwable): ResponseEntity<FileUploadResponseDto> {
        log.error("circuitBreakerCallback err = {}", throwable.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }

    fun retryCallback(throwable: Throwable): ResponseEntity<FileUploadResponseDto> {
        log.error("retryCallback err = {}", throwable.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
}