package socialapp.userservice.service;

import org.springframework.data.elasticsearch.core.SearchHits;
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

    SearchHits<User> find(UserSearchCriteria userSearchCriteria, int page, int pageSize);

    User findById(Long id);

}
