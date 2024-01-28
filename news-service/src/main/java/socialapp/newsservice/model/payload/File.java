package socialapp.newsservice.model.payload;

import java.time.LocalDateTime;

public record File(
        String id,
        String bucket,
        String fileName,
        String extension,
        LocalDateTime uploadedTime,
        String url,
        String attachmentSource,
        long target
) {}
