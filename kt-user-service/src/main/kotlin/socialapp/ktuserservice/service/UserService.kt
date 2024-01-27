package socialapp.ktuserservice.service

import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.web.multipart.MultipartFile
import socialapp.ktuserservice.model.dto.IsExistsResponse
import socialapp.ktuserservice.model.dto.RegistrationDto
import socialapp.ktuserservice.model.dto.UserSearchCriteria
import socialapp.ktuserservice.model.dto.UserUpdateDto
import socialapp.ktuserservice.model.entity.User

interface UserService {

    fun register(registrationDto: RegistrationDto): User

    fun delete(id: Long)

//    fun fetchSuggestions(query: String): Set<SuggestionResponse>

    fun isExists(email: String): IsExistsResponse

    fun uploadAvatar(file: MultipartFile, id: Long)

    fun update(userUpdateDto: UserUpdateDto, id: Long)

    fun find(userSearchCriteria: UserSearchCriteria, page: Int, pageSize: Int): SearchHits<User>

    fun findById(id: Long): User
}