package relucky.code.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    ResponseEntity<List<Channel>> getAll() {
        return ResponseEntity.ok(channelService.findAll());
    }
    @GetMapping("{id}")
    ResponseEntity<Channel> getChannel(@PathVariable String id) {
        return ResponseEntity.ok(channelService.findOne(id));
    }
    @PostMapping
    ResponseEntity<Channel> createChannel(@RequestBody ChannelCreateRequest channelCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(channelService.create(channelCreateRequest));
    }
}
