package relucky.code.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import relucky.code.model.enums.ChannelType;

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
    private String avatar;
    private String adminEmail;
    private List<Long> moderatorList;
    private List<Long> subscriberList;
}
