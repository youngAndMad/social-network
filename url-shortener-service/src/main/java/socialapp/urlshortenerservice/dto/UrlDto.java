package socialapp.urlshortenerservice.dto;

import lombok.Builder;

@Builder
public class UrlDto {
    private final String id;
    private final String url;
}
