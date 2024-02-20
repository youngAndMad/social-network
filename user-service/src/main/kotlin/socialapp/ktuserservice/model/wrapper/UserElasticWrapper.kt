package socialapp.ktuserservice.model.wrapper

import org.springframework.data.elasticsearch.annotations.Document
import socialapp.ktuserservice.common.AppConstants

@Document(indexName = AppConstants.USER_INDEX)
data class UserElasticWrapper(
    val id: Long,
    val givenName: String,
    val preferredUsername: String,
    val familyName: String,
    val email: String,
    val birthDate: String,
    val avatar: String,
) {
}