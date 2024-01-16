package socialapp.userservice.model.dto;

public record UserSearchCriteria(
        Integer ageFrom,
        Integer ageTo,
        String nameLike,
        String city,
        String country
) {

}
