package socialapp.chatservice.model.entity;

import lombok.Getter;
import lombok.Setter;
import socialapp.chatservice.model.dto.FileMetadata;

import java.util.List;

@Getter
@Setter
public class Message {

    private String id;
    private String content;
    private String sender;
    private List<FileMetadata> files;

}
