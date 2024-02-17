package socialapp.ktuserservice.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import socialapp.ktuserservice.model.dto.EmailResponseDto
import socialapp.ktuserservice.model.dto.UserDto
import socialapp.ktuserservice.model.dto.UserSearchCriteria
import socialapp.ktuserservice.model.entity.User
import socialapp.ktuserservice.service.UserService

@RestController
@RequestMapping("api/v1/user")
//@LoggableInfo
//@LoggableTime
class UserController(
        private var userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody @Valid userDto: UserDto): User =
            userService.register(userDto)

    @PutMapping("{id}")
    fun update(@RequestBody @Valid userUpdateDto: UserDto, @PathVariable id: Long) =
            userService.update(userUpdateDto, id)

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = userService.delete(id)

    @GetMapping("suggest")
    fun fetchSuggestions(@RequestParam query: String): Set<User> =
            userService.fetchSuggestions(query)


    @GetMapping("is-exists")
    fun isExist(@RequestParam @Email email: String) =
            userService.isExists(email)


    @PatchMapping("{id}/avatar")
    fun uploadAvatar(@RequestParam("file") file: MultipartFile, @PathVariable id: Long) =
            userService.uploadAvatar(file, id)


    @PostMapping("search")
    fun search(
            @RequestBody userSearchCriteria: UserSearchCriteria,
            @RequestParam(required = false) page: Int,
            @RequestParam(required = false, defaultValue = "10") pageSize: Int
    ): SearchHits<User> = userService.find(userSearchCriteria, page, pageSize)

    @GetMapping("emails")
    fun fetchEmails(
            @RequestParam(required = false) page: Int = 0,
            @RequestParam(required = false) pageSize: Int = 100
    ): List<EmailResponseDto> = userService.getEmailList(page, pageSize)

    @GetMapping("me")
    fun me(): User = userService.me()
}