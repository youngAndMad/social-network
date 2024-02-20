package relucky.code.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import relucky.code.common.client.StorageServiceClient;
import relucky.code.common.exception.EntityNotFoundException;
import relucky.code.model.entity.Channel;
import relucky.code.model.mapper.ChannelMapper;
import relucky.code.model.payload.ChannelCreateRequest;
import relucky.code.repository.ChannelRepository;
import relucky.code.service.ChannelService;

import java.util.List;
import java.util.Objects;

//import static relucky.code.utils.AuthenticationConvertUtils.extractAppUser;

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
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        channel.setAdminEmail(Objects.requireNonNull(extractAppUser(authentication)).getEmail());
        Channel savedFile = channelRepository.save(channel);

        storageServiceClient.uploadFiles("AVATAR", Long.valueOf(savedFile.getId()),List.of(multipartFile));
        return savedFile;
    }

}
