package socialapp.ktuserservice.repository.elastic

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.query.IndexQuery
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder
import org.springframework.stereotype.Repository
import socialapp.ktuserservice.common.AppConstants
import socialapp.ktuserservice.common.AppConstants.Companion.USER_INDEX
import socialapp.ktuserservice.common.mapper.UserMapper
import socialapp.ktuserservice.model.dto.UserSearchCriteria
import socialapp.ktuserservice.model.entity.User
import socialapp.ktuserservice.model.wrapper.UserElasticWrapper
import java.util.stream.Collectors

@Repository
class UserElasticRepository(
    private val elasticsearchOperations: ElasticsearchOperations,
    private var userMapper: UserMapper
) {
    private val log: Logger = LoggerFactory.getLogger(UserElasticRepository::class.java)

    fun insert(user: User) {
        val indexQuery: IndexQuery = indexQuery(user)
        log.info("Inserting user with id {}", user.id)
        elasticsearchOperations.index(indexQuery, IndexCoordinates.of(USER_INDEX))
    }

    fun update(user: User) {
        val indexQuery: IndexQuery = indexQuery(user)
        log.info("Updating user with id {}", user.id)
        elasticsearchOperations.index(indexQuery, IndexCoordinates.of(USER_INDEX))
    }

    private fun indexQuery(user: User): IndexQuery = IndexQueryBuilder()
        .withId(user.id.toString())
        .withObject(userMapper.toWrapper(user))
        .build()

    fun delete(id: Long) {
        log.info("Deleting user with id {}", id)
        elasticsearchOperations.delete(id.toString(), IndexCoordinates.of(USER_INDEX))
    }

    fun fetchSuggestions(query: String): Set<UserElasticWrapper> {
        val usernameCriteria = Criteria.where("preferredUsername").contains(query)
        val criteriaQuery = CriteriaQuery(usernameCriteria)

        return elasticsearchOperations.search(criteriaQuery, UserElasticWrapper::class.java, IndexCoordinates.of(USER_INDEX))
            .stream()
            .map { it.content }
            .collect(Collectors.toSet())
    }

    fun findByFilter(userSearchCriteria: UserSearchCriteria, page: Int, pageSize: Int): SearchHits<UserElasticWrapper> {

        val usernameCriteria =
            userSearchCriteria.username?.let { Criteria.where(AppConstants.USERNAME).greaterThan(it) }
        val ageFromCriteria = userSearchCriteria.ageFrom?.let { Criteria.where(AppConstants.AGE).greaterThan(it) }
        val ageToCriteria = userSearchCriteria.ageTo?.let { Criteria.where(AppConstants.AGE).lessThan(it) }
        val cityCriteria = userSearchCriteria.city?.let { Criteria.where(AppConstants.CITY).`is`(it) }
        val countryCriteria = userSearchCriteria.country?.let { Criteria.where(AppConstants.COUNTRY).`is`(it) }

        val finalCriteria =
            listOfNotNull(usernameCriteria, ageFromCriteria, ageToCriteria, cityCriteria, countryCriteria)
                .reduce { acc, criteria -> acc.and(criteria) }

        val criteriaQuery = CriteriaQuery(finalCriteria)
            .setPageable<CriteriaQuery>(PageRequest.of(page, pageSize))

        return elasticsearchOperations.search(criteriaQuery, UserElasticWrapper::class.java, IndexCoordinates.of(USER_INDEX))
    }

}