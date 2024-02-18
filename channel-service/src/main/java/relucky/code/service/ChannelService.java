package relucky.code.service;

import relucky.code.model.entity.Channel;
import relucky.code.model.payload.ChannelCreateRequest;

import java.util.List;

public interface ChannelService {
    List<Channel> findAll();
    Channel findOne(String id);

    void delete(String id);
    Channel create(ChannelCreateRequest request);
}
