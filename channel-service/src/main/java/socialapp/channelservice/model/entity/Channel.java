package socialapp.channelservice.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import socialapp.channelservice.model.enums.ChannelType;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Builder
public class Channel {
    @Id
    private String id;
    private String name;
    private ChannelType channelType;
    private String avatarUrl;
    private AppUser admin;
    private List<AppUser> moderatorList;
    private List<AppUser> subscriberList;
}
