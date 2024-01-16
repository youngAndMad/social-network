package socialapp.userservice.service;

import org.springframework.web.multipart.MultipartFile;
import socialapp.userservice.model.dto.*;
import socialapp.userservice.model.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User register(RegistrationDto registrationDto);

    void delete(Long id);

    Set<SuggestionResponse> fetchSuggestions(String query);

    IsExistsResponse isExists(String email);

    void uploadAvatar(MultipartFile file, Long id);

    void update(UserUpdateDto userUpdateDto,Long id);

    Set<User> find(UserSearchCriteria userSearchCriteria);

}
