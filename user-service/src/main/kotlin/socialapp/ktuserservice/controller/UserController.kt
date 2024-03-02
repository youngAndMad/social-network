package socialapp.ktuserservice.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import socialapp.ktuserservice.model.dto.EmailResponseDto
import socialapp.ktuserservice.model.dto.UserDto
import socialapp.ktuserservice.model.dto.UserResponseDto
import socialapp.ktuserservice.model.dto.UserSearchCriteria
import socialapp.ktuserservice.model.wrapper.UserElasticWrapper
import socialapp.ktuserservice.service.RelationService
import socialapp.ktuserservice.service.UserService

@RestController
@RequestMapping("api/v1/user")
class UserController(
    private var userService: UserService,
    private var relationService: RelationService
) {

    @GetMapping("{id}")
    fun getUser(@PathVariable id: Long) = userService.findById(id)

    @PutMapping("{id}")
    fun update(@RequestBody @Valid userUpdateDto: UserDto, @PathVariable id: Long) =
        userService.update(userUpdateDto, id)

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = userService.delete(id)

    @GetMapping("suggest")
    fun fetchSuggestions(@RequestParam query: String): Set<UserElasticWrapper> =
        userService.fetchSuggestions(query)


    @GetMapping("is-exists")
    fun isExist(@RequestParam @Email email: String) =
        userService.isExists(email)


    @PatchMapping("{id}/avatar")
    fun uploadAvatar(@RequestParam("file") file: MultipartFile, @PathVariable id: Long) =
        userService.uploadAvatar(file, id)

    @DeleteMapping("{id}/avatar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAvatar(@PathVariable id: Long) = userService.deleteAvatar(id)

    @PostMapping("search")
    fun search(
        @RequestBody userSearchCriteria: UserSearchCriteria,
        @RequestParam(required = false) page: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int
    ): SearchHits<UserElasticWrapper> = userService.find(userSearchCriteria, page, pageSize)

    @GetMapping("emails")
    fun fetchEmails(
        @RequestParam(required = false) page: Int = 0,
        @RequestParam(required = false) pageSize: Int = 100
    ): List<EmailResponseDto> = userService.getEmailList(page, pageSize)

    @GetMapping("me")
    fun me(@RequestHeader("Authorization") authHeader: String): UserResponseDto {
        val me = userService.me()
        return UserResponseDto(me, relationService.findUserRelations(me.id!!))
    }
}