package socialapp.urlshortenerservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a URL with an identifier and the actual URL value.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class URL {
    private String id;
    private String url;
}
