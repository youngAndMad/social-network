package socialapp.ktuserservice.service

import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.web.multipart.MultipartFile
import socialapp.ktuserservice.model.dto.*
import socialapp.ktuserservice.model.entity.User

interface UserService {

    fun delete(id: Long)

    fun fetchSuggestions(query: String): Set<User>

    fun isExists(email: String): IsExistsResponse

    fun uploadAvatar(file: MultipartFile, id: Long)

    fun update(userUpdateDto: UserDto, id: Long)

    fun find(userSearchCriteria: UserSearchCriteria, page: Int, pageSize: Int): SearchHits<User>

    fun findById(id: Long): User

    fun getEmailList(page: Int,pageSize: Int): List<EmailResponseDto>

    fun me():User

    fun deleteAvatar(id: Long)
}