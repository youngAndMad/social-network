package socialapp.urlshortenerservice.model.entity;

import lombok.Builder;

@Builder
public class URL {
    private final String id;
    private final String url;
}
