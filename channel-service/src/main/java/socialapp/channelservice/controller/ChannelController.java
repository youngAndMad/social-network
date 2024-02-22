package socialapp.channelservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import socialapp.channelservice.model.entity.AppUser;
import socialapp.channelservice.model.entity.Channel;
import socialapp.channelservice.model.enums.ChannelType;
import socialapp.channelservice.model.payload.ChannelUpdateRequest;
import socialapp.channelservice.service.ChannelService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping
    Page<Channel> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        return channelService.findAll(page, pageSize);
    }

    @GetMapping("{id}")
    Channel getChannel(@PathVariable String id) {
        return channelService.findOne(id);
    }

    @PostMapping
    ResponseEntity<Channel> createChannel(
            @RequestParam @NotNull @NotEmpty String name,
            @RequestParam @NotNull ChannelType type,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(channelService.create(name, type, file));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteChannel(@PathVariable String id) {
        channelService.delete(id);
    }

    @PatchMapping("{id}")
    void updateChannel(@RequestBody @Valid ChannelUpdateRequest request, @PathVariable String id) {
        channelService.updateChannel(request, id);
    }

    @PutMapping("{id}")
    void updateAvatar(@RequestParam("file") MultipartFile multipartFile, @PathVariable String id) {
        channelService.updateAvatar(multipartFile, id);
    }

    @DeleteMapping("{id}/avatar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAvatar(@PathVariable String id) {
        channelService.deleteAvatar(id);
    }

    @GetMapping("my-channels")
    Set<Channel> getMyChannels() {
        return channelService.currentUserChannels();
    }

    @PostMapping("{id}/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    void subscribe(@PathVariable String id) {
        channelService.subscribe(id);
    }

    @DeleteMapping("{id}/unsubscribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void unsubscribe(@PathVariable String id) {
        channelService.unsubscribe(id);
    }

    @PostMapping("{id}/moderator")
    @ResponseStatus(HttpStatus.CREATED)
    void addModerator(@PathVariable String id, @RequestBody AppUser appUser) {
        channelService.addModerator(id, appUser);
    }

    @DeleteMapping("{id}/moderator")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeModerator(@PathVariable String id, @RequestParam String email) {
        channelService.removeModerator(id, email);
    }
}


