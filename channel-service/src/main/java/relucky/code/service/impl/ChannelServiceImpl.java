package relucky.code.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import relucky.code.common.exception.EntityNotFoundException;
import relucky.code.model.entity.Channel;
import relucky.code.model.mapper.ChannelMapper;
import relucky.code.model.payload.ChannelCreateRequest;
import relucky.code.repository.ChannelRepository;
import relucky.code.service.ChannelService;

import java.util.List;

import static org.springframework.security.oauth2.core.oidc.OidcScopes.EMAIL;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;

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
    public Channel create(ChannelCreateRequest request) {
        return channelRepository.save(channelMapper.toModel(request));
    }

}
