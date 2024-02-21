package socialapp.channelservice.model.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import socialapp.channelservice.model.enums.ChannelType;

public record ChannelUpdateRequest(
        @NotEmpty @NotNull String name,
        @NotNull ChannelType channelType
) {
}
