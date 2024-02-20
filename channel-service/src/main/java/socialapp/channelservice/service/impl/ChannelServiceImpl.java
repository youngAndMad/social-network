package socialapp.channelservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import socialapp.channelservice.common.client.StorageServiceClient;
import socialapp.channelservice.common.exception.EntityNotFoundException;
import socialapp.channelservice.model.entity.Channel;
import socialapp.channelservice.model.enums.ChannelType;
import socialapp.channelservice.model.mapper.ChannelMapper;
import socialapp.channelservice.model.payload.FileUploadResponse;
import socialapp.channelservice.repository.ChannelRepository;
import socialapp.channelservice.service.ChannelService;

import java.util.List;
import java.util.Objects;

import static socialapp.channelservice.utils.AuthenticationConvertUtils.extractAppUser;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;
    private final StorageServiceClient storageServiceClient;


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
        Channel channel = Channel.builder()
                .name(name)
                .channelType(channelType)
                .adminEmail(Objects.requireNonNull(extractAppUser(authentication)).getEmail())
                .build();

        Channel savedFile = channelRepository.save(channel);
        log.info(savedFile.getId());
        ResponseEntity<FileUploadResponse[]> userProfileImage = storageServiceClient.uploadFiles("USER_PROFILE_IMAGE", savedFile.getId(), List.of(multipartFile));
        FileUploadResponse[] fileUploadResponses = userProfileImage.getBody();
        if (fileUploadResponses != null) {
            for (FileUploadResponse fileUploadResponse : fileUploadResponses) {
                String url = fileUploadResponse.url();
                log.info(url);
                savedFile.setAvatar(url);

            }
        }
        return savedFile;
    }

}
