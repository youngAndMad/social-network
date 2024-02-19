package relucky.code.service;

import org.springframework.data.domain.Page;
import relucky.code.model.entity.Channel;
import relucky.code.model.payload.ChannelCreateRequest;

import java.util.List;

public interface ChannelService {
    Page<Channel> findAll(int page, int pageSize);
    Channel findOne(String id);

    void delete(String id);
    Channel create(ChannelCreateRequest request);
}
