package socialapp.channelservice.model.mapper;

import org.mapstruct.Mapper;
import socialapp.channelservice.model.entity.Channel;
import socialapp.channelservice.model.payload.ChannelCreateRequest;

@Mapper
public interface ChannelMapper {
    Channel toModel(ChannelCreateRequest channelCreateRequest);
}
