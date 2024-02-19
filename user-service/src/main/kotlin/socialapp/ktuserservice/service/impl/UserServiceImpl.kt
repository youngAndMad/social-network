package socialapp.ktuserservice.service.impl

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import socialapp.ktuserservice.common.AppConstants.Companion.AGE
import socialapp.ktuserservice.common.AppConstants.Companion.CITY
import socialapp.ktuserservice.common.AppConstants.Companion.COUNTRY
import socialapp.ktuserservice.common.AppConstants.Companion.USERNAME
import socialapp.ktuserservice.common.AppConstants.Companion.USER_INDEX
import socialapp.ktuserservice.common.AppConstants.Companion.USER_PROFILE_IMAGE
import socialapp.ktuserservice.common.client.StorageClient
import socialapp.ktuserservice.common.mapper.UserMapper
import socialapp.ktuserservice.model.dto.*
import socialapp.ktuserservice.model.entity.User
import socialapp.ktuserservice.repository.UserRepository
import socialapp.ktuserservice.service.AddressService
import socialapp.ktuserservice.service.UserService
import java.util.stream.Collectors

@Service
class UserServiceImpl(
    private var userRepository: UserRepository,
    private var elasticsearchOperations: ElasticsearchOperations,
    private var addressService: AddressService,
    private var storageClient: StorageClient,
    private var userMapper: UserMapper
) : UserService {

    private companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun register(userDto: UserDto): User {
        val existsByEmail = userRepository.existsByEmail(userDto.email)

        if (existsByEmail) {
            return userRepository.findByEmail(userDto.email)!! // ???
        }

        val address = addressService.save(userDto.address)
        val user = userMapper.toModel(userDto, address)
        return userRepository.save(user)
    }

    override fun delete(id: Long) = userRepository.deleteById(id)

    override fun fetchSuggestions(query: String): Set<User> {
        val usernameCriteria = Criteria.where(USERNAME).contains(query)
        val criteriaQuery = CriteriaQuery(usernameCriteria)

        return elasticsearchOperations.search(criteriaQuery, User::class.java, IndexCoordinates.of(USER_INDEX))
            .stream()
            .map { it.content }
            .collect(Collectors.toSet())
    }


    override fun isExists(email: String): IsExistsResponse {
        val user = userRepository.findByEmail(email)

        return IsExistsResponse(user != null, user)
    }


    override fun uploadAvatar(file: MultipartFile, id: Long) {
        val user = findById(id)
        val uploadedAvatar = storageClient.upload(USER_PROFILE_IMAGE, user.id!!, file)

        if (uploadedAvatar.statusCode.is2xxSuccessful && uploadedAvatar.body != null) {
            user.avatar = uploadedAvatar.body?.url
            userRepository.save(user)
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

        userRepository.save(user)
    }

    override fun find(userSearchCriteria: UserSearchCriteria, page: Int, pageSize: Int): SearchHits<User> {

        val usernameCriteria = userSearchCriteria.username?.let { Criteria.where(USERNAME).greaterThan(it) }
        val ageFromCriteria = userSearchCriteria.ageFrom?.let { Criteria.where(AGE).greaterThan(it) }
        val ageToCriteria = userSearchCriteria.ageTo?.let { Criteria.where(AGE).lessThan(it) }
        val cityCriteria = userSearchCriteria.city?.let { Criteria.where(CITY).`is`(it) }
        val countryCriteria = userSearchCriteria.country?.let { Criteria.where(COUNTRY).`is`(it) }

        val finalCriteria =
            listOfNotNull(usernameCriteria, ageFromCriteria, ageToCriteria, cityCriteria, countryCriteria)
                .reduce { acc, criteria -> acc.and(criteria) }

        val criteriaQuery = CriteriaQuery(finalCriteria)
            .setPageable<CriteriaQuery>(PageRequest.of(page, pageSize))

        return elasticsearchOperations.search(criteriaQuery, User::class.java, IndexCoordinates.of(USER_INDEX))
    }

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
    }

    override fun deleteAvatar(id: Long) {
        val user = findById(id)
        user.avatar = null
        userRepository.save(user)
//        storageClient.deleteAvatar(id) fixme create endpoint in storage service
    }
}