package relucky.code.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import relucky.code.common.exception.EntityNotFoundException;
import relucky.code.model.entity.Channel;
import relucky.code.model.payload.ChannelCreateRequest;
import relucky.code.repository.ChannelRepository;
import relucky.code.service.ChannelService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;


    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
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
    public Channel create(ChannelCreateRequest request) {
        Channel channel = Channel.builder()
                .name(request.name())
                .adminId(request.adminId())
                .channelType(request.channelType())
                .avatar(request.avatar())
                .build();
        channelRepository.save(channel);
        return channel;
    }

}
