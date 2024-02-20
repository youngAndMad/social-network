package socialapp.channelservice.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import socialapp.channelservice.model.entity.Channel;
import socialapp.channelservice.model.enums.ChannelType;
import socialapp.channelservice.model.payload.ChannelCreateRequest;

public interface ChannelService {
    Page<Channel> findAll(int page, int pageSize);
    Channel findOne(String id);

    void delete(String id);
    Channel create(String name, ChannelType channelType, MultipartFile file);
}
