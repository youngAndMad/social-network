package socialapp.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import socialapp.userservice.service.RelationService;

@RestController
@RequestMapping("api/v1/relation")
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    @DeleteMapping("/block/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBlock(@PathVariable Long id) {
        relationService.deleteBlock(id);
    }

    @DeleteMapping("/subscription/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSubscription(@PathVariable Long id) {
        relationService.deleteSubscription(id);
    }

    @DeleteMapping("/friendship/{id}/by/{by}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteFriendship(@PathVariable Long id, @PathVariable Long by) {
        relationService.deleteFriendship(id, by);
    }

    @PostMapping("/block")
    @ResponseStatus(HttpStatus.CREATED)
    void blockUser(@RequestParam Long sender, @RequestParam Long receiver) {
        relationService.blockUser(sender, receiver);
    }

    @PostMapping("/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    void subscribeUser(@RequestParam Long sender, @RequestParam Long receiver) {
        relationService.subscribeUser(sender, receiver);
    }

    @PostMapping("/friendship")
    @ResponseStatus(HttpStatus.CREATED)
    void addFriendship(@RequestParam Long sender, @RequestParam Long receiver) {
        relationService.addFriendship(sender, receiver);
    }
}
