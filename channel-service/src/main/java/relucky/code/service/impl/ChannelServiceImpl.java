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
        Channel channel = new Channel();
        channel.setName(request.name());
        channel.setChannelType(request.channelType());
        channel.setAvatar(request.avatar());
        channel.setAdminId(request.adminId());
        channelRepository.save(channel);
        return channel;
    }

}
