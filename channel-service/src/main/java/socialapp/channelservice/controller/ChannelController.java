package socialapp.channelservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import socialapp.channelservice.model.entity.Channel;
import socialapp.channelservice.model.enums.ChannelType;
import socialapp.channelservice.model.payload.ChannelUpdateRequest;
import socialapp.channelservice.service.ChannelService;

@RestController
@RequestMapping("/api/v1/channel")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @GetMapping
    ResponseEntity<Page<Channel>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(channelService.findAll(page, pageSize));
    }

    @GetMapping("{id}")
    ResponseEntity<Channel> getChannel(@PathVariable String id) {
        return ResponseEntity.ok(channelService.findOne(id));
    }

    @PostMapping
    ResponseEntity<Channel> createChannel(@RequestParam String name,
                                          @RequestParam ChannelType type,
                                          @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(channelService.create(name, type, file));
    }
    @DeleteMapping("{id}")
    ResponseEntity<HttpStatus> deleteChannel(@PathVariable String id){
        channelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    ResponseEntity<HttpStatus> updateChannel(@RequestBody ChannelUpdateRequest request, @PathVariable String id){
        channelService.updateChannel(request, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    ResponseEntity<HttpStatus> updateAvatar(@RequestParam("file") MultipartFile multipartFile, @PathVariable String id){
        channelService.updateAvatar(multipartFile, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/avatar")
    ResponseEntity<HttpStatus> deleteAvatar(@PathVariable String id){
        channelService.deleteAvatar(id);
        return ResponseEntity.noContent().build();
    }
}
