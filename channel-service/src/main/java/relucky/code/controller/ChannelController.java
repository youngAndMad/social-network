package relucky.code.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import relucky.code.model.entity.Channel;
import relucky.code.model.payload.ChannelCreateRequest;
import relucky.code.service.ChannelService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController {
    private final ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

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
    ResponseEntity<Channel> createChannel(@RequestBody ChannelCreateRequest channelCreateRequest,
                                          @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(channelService.create(channelCreateRequest, file));
    }
}
