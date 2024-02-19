package relucky.code.model.mapper;

import org.mapstruct.Mapper;
import relucky.code.model.entity.Channel;
import relucky.code.model.payload.ChannelCreateRequest;

@Mapper
public interface ChannelMapper {
    Channel toModel(ChannelCreateRequest channelCreateRequest);
}
