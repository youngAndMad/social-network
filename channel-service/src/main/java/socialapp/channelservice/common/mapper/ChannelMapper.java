package socialapp.channelservice.common.mapper;

import org.mapstruct.Mapper;
import socialapp.channelservice.model.entity.Channel;
import socialapp.channelservice.model.payload.ChannelUpdateRequest;

@Mapper
public interface ChannelMapper {
    Channel toModel(ChannelUpdateRequest channelUpdateRequest);
}
