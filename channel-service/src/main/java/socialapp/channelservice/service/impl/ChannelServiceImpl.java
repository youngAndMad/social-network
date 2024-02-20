package socialapp.channelservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import socialapp.channelservice.common.client.StorageServiceClient;
import socialapp.channelservice.common.exception.EntityNotFoundException;
import socialapp.channelservice.model.entity.Channel;
import socialapp.channelservice.model.mapper.ChannelMapper;
import socialapp.channelservice.model.payload.ChannelCreateRequest;
import socialapp.channelservice.repository.ChannelRepository;
import socialapp.channelservice.service.ChannelService;

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
        if (channelRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException(Channel.class, id);
        }
        channelRepository.deleteById(id);
    }

    @Override
    public Channel create(ChannelCreateRequest request, MultipartFile multipartFile) {
        Channel channel = channelMapper.toModel(request);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        channel.setAdminEmail(Objects.requireNonNull(extractAppUser(authentication)).getEmail());
        Channel savedFile = channelRepository.save(channel);
//        storageServiceClient.uploadFiles("AVATAR", Long.valueOf(savedFile.getId()),List.of(multipartFile));
        return savedFile;
    }

}
