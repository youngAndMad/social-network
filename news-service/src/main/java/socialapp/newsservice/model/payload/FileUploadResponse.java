package socialapp.newsservice.model.payload;

public record FileUploadResponse(
       String url,
       String id,
       String extension
) {
}
