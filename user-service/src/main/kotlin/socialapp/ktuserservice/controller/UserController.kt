package socialapp.ktuserservice.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import socialapp.ktuserservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import socialapp.ktuserservice.model.dto.*
import socialapp.ktuserservice.model.entity.User

@RestController
@RequestMapping("api/v1/user")
class UserController(
    private var userService: UserService
) {

    @PostMapping
    fun register(@RequestBody @Valid registrationDto: RegistrationDto): ResponseEntity<User> =
        ResponseEntity.status(HttpStatus.CREATED).body(userService.register(registrationDto))


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody @Valid userUpdateDto: UserUpdateDto, @PathVariable id: Long) =
        userService.update(userUpdateDto, id)

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = userService.delete(id)


    @GetMapping("suggest")
    fun fetchSuggestions(@RequestParam query: String): ResponseEntity<Set<User>> =
        ResponseEntity.ok(userService.fetchSuggestions(query))


    @GetMapping("is-exists")
    fun isExist(@RequestParam @Email email: String): ResponseEntity<IsExistsResponse> =
        ResponseEntity.ok(userService.isExists(email))


    @PatchMapping("{id}/avatar")
    fun uploadAvatar(@RequestParam("file") file: MultipartFile, @PathVariable id: Long) =
        userService.uploadAvatar(file, id)


    @PostMapping("search")
    fun search(
        @RequestBody userSearchCriteria: UserSearchCriteria,
        @RequestParam(required = false) page: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int
    ): ResponseEntity<SearchHits<User>> = ResponseEntity.ok(userService.find(userSearchCriteria, page, pageSize))

    @GetMapping("emails")
    fun fetchEmails(
        @RequestParam(required = false) page: Int,
        @RequestParam(required = false, defaultValue = "100") pageSize: Int
    ): ResponseEntity<List<EmailResponseDto>> = ResponseEntity.ok(userService.getEmailList(page, pageSize))


}