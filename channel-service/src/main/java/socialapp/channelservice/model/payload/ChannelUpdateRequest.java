package socialapp.channelservice.model.payload;

import socialapp.channelservice.model.enums.ChannelType;


public record ChannelUpdateRequest(String name,
                                   ChannelType channelType) {
}
