package socialapp.channelservice.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import socialapp.channelservice.model.entity.Channel;
import socialapp.channelservice.model.enums.ChannelType;
import socialapp.channelservice.model.payload.ChannelUpdateRequest;

public interface ChannelService {
    Page<Channel> findAll(int page, int pageSize);
    Channel findOne(String id);
    void delete(String id);
    Channel create(String name, ChannelType channelType, MultipartFile file);
    void updateChannel(ChannelUpdateRequest request, String id);
    void updateAvatar(MultipartFile multipartFile, String id);

    void deleteAvatar(String id);
}
