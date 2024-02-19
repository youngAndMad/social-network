package relucky.code.model.payload;

import relucky.code.model.enums.ChannelType;

public record ChannelCreateRequest(String name,
                                   ChannelType channelType,
                                   String avatar) {
}
