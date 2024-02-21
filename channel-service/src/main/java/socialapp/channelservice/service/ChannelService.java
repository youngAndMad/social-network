package socialapp.channelservice.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import socialapp.channelservice.model.entity.AppUser;
import socialapp.channelservice.model.entity.Channel;
import socialapp.channelservice.model.enums.ChannelType;
import socialapp.channelservice.model.payload.ChannelUpdateRequest;

import java.util.List;
import java.util.Set;

public interface ChannelService {
    Page<Channel> findAll(int page, int pageSize);

    Channel findOne(String id);

    void delete(String id);

    Channel create(String name, ChannelType channelType, MultipartFile file);

    Set<Channel> currentUserChannels();

    void updateChannel(ChannelUpdateRequest request, String id);

    void updateAvatar(MultipartFile multipartFile, String id);

    void deleteAvatar(String id);

    void subscribe(String id);

    void unsubscribe(String id);

    void addModerator(String id, AppUser appUser);

    void removeModerator(String id,String  email);
}
