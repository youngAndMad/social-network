package socialapp.channelservice.model.payload;

import socialapp.channelservice.model.enums.ChannelType;


public record ChannelCreateRequest(String name,
                                   ChannelType channelType) {
}
