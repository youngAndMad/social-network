package socialapp.authservice.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDetails{
    private LocalDateTime thrownAt;
    private String message;
    private String path;
    private HttpStatus status;
}
