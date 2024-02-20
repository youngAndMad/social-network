package relucky.code.model.payload;

import org.springframework.web.multipart.MultipartFile;
import relucky.code.model.enums.ChannelType;


public record ChannelCreateRequest(String name,
                                   ChannelType channelType) {
}
