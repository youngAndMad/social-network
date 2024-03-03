package socialapp.ktuserservice.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import socialapp.ktuserservice.model.dto.UserRelationsDto
import socialapp.ktuserservice.service.RelationService


@RestController
@RequestMapping("api/v1/user/relation")
class RelationController(
    private var relationService: RelationService
) {

    @DeleteMapping("/block/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBlock(@PathVariable id: Long) = relationService.deleteBlock(id)


    @DeleteMapping("/subscription/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSubscription(@PathVariable id: Long) = relationService.deleteSubscription(id)


    @DeleteMapping("/friendship/{id}/by/{by}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteFriendship(@PathVariable id: Long, @PathVariable by: Long) = relationService.deleteFriendship(id, by)


    @PostMapping("/block")
    @ResponseStatus(HttpStatus.CREATED)
    fun blockUser(@RequestParam sender: Long, @RequestParam receiver: Long) =
        relationService.blockUser(sender, receiver)


    @PostMapping("/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    fun subscribeUser(@RequestParam sender: Long, @RequestParam receiver: Long) =
        relationService.subscribeUser(sender, receiver)


    @PostMapping("/friendship")
    @ResponseStatus(HttpStatus.CREATED)
    fun addFriendship(@RequestParam sender: Long, @RequestParam receiver: Long) =
        relationService.addFriendship(sender, receiver)


    @GetMapping("relations")
    fun userRelations(@RequestParam userId: Long): ResponseEntity<UserRelationsDto> =
        ResponseEntity.ok(relationService.findUserRelations(userId))

}