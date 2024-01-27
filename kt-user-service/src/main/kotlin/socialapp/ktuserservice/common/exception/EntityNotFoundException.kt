package socialapp.ktuserservice.common.exception

class EntityNotFoundException(entityClass: Class<*>, id: Any) : RuntimeException(
    "Entity ${entityClass.simpleName} with id $id not found"
)