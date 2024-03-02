package socialapp.ktuserservice.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import socialapp.ktuserservice.common.AppConstants.Companion.USER_PROFILE_IMAGE
import socialapp.ktuserservice.common.client.StorageClient
import socialapp.ktuserservice.common.mapper.UserMapper
import socialapp.ktuserservice.model.dto.*
import socialapp.ktuserservice.model.entity.User
import socialapp.ktuserservice.model.wrapper.UserElasticWrapper
import socialapp.ktuserservice.repository.UserRepository
import socialapp.ktuserservice.repository.elastic.UserElasticRepository
import socialapp.ktuserservice.service.AddressService
import socialapp.ktuserservice.service.RelationService
import socialapp.ktuserservice.service.UserService

@Service
class UserServiceImpl(
    private var userRepository: UserRepository,
    private var addressService: AddressService,
    private var storageClient: StorageClient,
    private var userMapper: UserMapper,
//    private var relationService: RelationServiceImpl,
    private var userElasticRepository: UserElasticRepository
) : UserService {

    private companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun delete(id: Long) = userRepository.deleteById(id)

    override fun fetchSuggestions(query: String) = userElasticRepository.fetchSuggestions(query)


    override fun isExists(email: String): IsExistsResponse {
        val user = userRepository.findByEmail(email)

        return IsExistsResponse(user != null, user)
    }


    override fun uploadAvatar(file: MultipartFile, id: Long) {
        val user = findById(id)
        val uploadedAvatar = storageClient.upload(USER_PROFILE_IMAGE, user.id!!, file)

        if (uploadedAvatar.statusCode.is2xxSuccessful && uploadedAvatar.body != null) {
            log.info("avatar uploaded successfully {}", uploadedAvatar.body?.url)
            user.avatar = uploadedAvatar.body?.url
            userRepository.save(user).let{
                userElasticRepository.update(it)
            }
        } else {
            log.error("storage client error, status =  {}", uploadedAvatar.statusCode)
        }
    }

    override fun update(userUpdateDto: UserDto, id: Long) {
        val user = findById(id)

        if (user.address == null) {
            user.address = addressService.save(userUpdateDto.address)
        } else {
            addressService.update(userUpdateDto.address, user.address!!)
        }
        userMapper.update(user, userUpdateDto)

        userRepository.save(user).let {
            userElasticRepository.update(it)
        }
    }

    override fun find(userSearchCriteria: UserSearchCriteria, page: Int, pageSize: Int): SearchHits<UserElasticWrapper> =
        userElasticRepository.findByFilter(userSearchCriteria, page, pageSize)

    override fun findById(id: Long): User = userRepository.findByID(id)

    override fun getEmailList(page: Int, pageSize: Int): List<EmailResponseDto> =
        userRepository.findAll(PageRequest.of(page, pageSize))
            .content
            .map { socialapp.ktuserservice.model.dto.EmailResponseDto(it.email!!) }.toList()

    override fun me(): User {
        val auth = SecurityContextHolder.getContext().authentication
        val jwt = auth.principal as Jwt
        val email = jwt.getClaimAsString("email")!!
        return this.userRepository.findByEmail(email)!!
//        return UserResponseDto(user, null!!)
    }

    override fun deleteAvatar(id: Long) {
        val user = findById(id)
        user.avatar = null
        userRepository.save(user).let {
            userElasticRepository.update(it)
        }
//        storageClient.deleteAvatar(id) fixme create endpoint in storage service
    }

}