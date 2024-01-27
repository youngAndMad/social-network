package socialapp.ktuserservice.model.dto

import socialapp.ktuserservice.model.enums.RelationStatus


data class RelationRequestDto(
        val from: Long,
        val receiver: Long,
        val status: RelationStatus
)
