package socialapp.channelservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import socialapp.channelservice.common.client.StorageServiceClient;
import socialapp.channelservice.common.exception.EntityNotFoundException;
import socialapp.channelservice.model.entity.Channel;
import socialapp.channelservice.model.enums.ChannelType;
import socialapp.channelservice.model.payload.ChannelUpdateRequest;
import socialapp.channelservice.model.payload.FileUploadResponse;
import socialapp.channelservice.repository.ChannelRepository;
import socialapp.channelservice.service.ChannelService;

import java.util.Objects;

import static socialapp.channelservice.common.AppConstants.SOURCE_NAME;
import static socialapp.channelservice.utils.AuthenticationConvertUtils.extractAppUser;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final StorageServiceClient storageServiceClient;
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Channel> findAll(
            int page, int pageSize
    ) {
        return channelRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Channel findOne(String id) {
        return channelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Channel.class, id));
    }

    @Override
    public void delete(String id) {
        if (!channelRepository.existsById(id)) {
            throw new EntityNotFoundException(Channel.class, id);
        }
        channelRepository.deleteById(id);
    }

    @Override
    public Channel create(String name, ChannelType channelType, MultipartFile multipartFile) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var savedFile = channelRepository.save(Channel.builder()
                .name(name)
                .channelType(channelType)
                .adminEmail(Objects.requireNonNull(extractAppUser(authentication)).getEmail())
                .build());

        if (multipartFile == null) {
            return savedFile;
        }

        return proceedChannelAvatarUploading(multipartFile, savedFile);
    }

    @Override
    public void updateChannel(ChannelUpdateRequest request, String id) {
        var channel = findOne(id);
        channel.setName(request.name());
        channel.setChannelType(request.channelType());
        channelRepository.save(channel);
    }

    @Override
    public void updateAvatar(MultipartFile multipartFile, String id) {
        Channel channel = findOne(id);
        proceedChannelAvatarUploading(multipartFile, channel);
    }

    private Channel proceedChannelAvatarUploading(MultipartFile multipartFile, Channel channel) {
        var responseEntity = storageServiceClient.uploadFile(SOURCE_NAME, channel.getId(), multipartFile);
        channel.setAvatarUrl(Objects.requireNonNull(responseEntity.getBody()).url());
        return channelRepository.save(channel);
    }

    @Override
    public void deleteAvatar(String id) {
        if (!channelRepository.existsById(id)) {
            throw new EntityNotFoundException(Channel.class, id);
        }
        var query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        var update = new Update();
        update.unset("avatarUrl");
        mongoTemplate.updateFirst(query, update, Channel.class);
    }

}
